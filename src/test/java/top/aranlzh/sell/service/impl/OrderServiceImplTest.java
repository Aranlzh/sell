package top.aranlzh.sell.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import top.aranlzh.sell.dataobject.OrderDetail;
import top.aranlzh.sell.dto.OrderDTO;
import top.aranlzh.sell.dto.PayTypeDetailDTO;
import top.aranlzh.sell.dto.ProductSaleDetailDTO;
import top.aranlzh.sell.dto.TimeSaleDetailDTO;
import top.aranlzh.sell.enums.OrderStatusEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//import top.aranlzh.sell.enums.PayStatusEnum;

/**
 * @author: Aranlzh
 * @date: 2019/3/13
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final Integer TABLE_ID = 3;

    private final String ORDER_ID = "1552453826562604882";

    @Test
    public void create() throws Exception {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTableId(2);
//        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123456");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("234567");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() throws Exception {
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】result={}", result);
        Assert.assertEquals(ORDER_ID, result.getOrderId());
    }

//    @Test
//    public void findList() throws Exception {
//        PageRequest request = new PageRequest(0,2);
//        Page<OrderDTO> orderDTOPage = orderService.findList(TABLE_ID, request);
//        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
//    }

    @Test
    public void cancel() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
//        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }

    @Test
    public void list() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
//        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
        Assert.assertTrue("查询所有的订单列表", orderDTOPage.getTotalElements() > 0);
    }

    @Test
    public void findDateOrderList(){
        PageRequest request = new PageRequest(0,2);
        String str = "2019-03-25";
        Page<OrderDTO> orderDTOPage = orderService.findOneDayOrderList(str,request);
        log.info("orderDTOPage={}",orderDTOPage);
        Assert.assertTrue("查询所有的订单列表", orderDTOPage.getTotalElements() > 0);
    }

    @Test
    public void countNum(){
        String date = "2019-03-25";
        Integer num = orderService.countByCreateTimeBetween(date,date);
        log.info("num={}",num);
    }

    @Test
    public void countAmount(){
        String str = "2019-03-25";
        BigDecimal bigDecimal = orderService.sumOrderAmountByCreateTimeBetween(str,str);
        log.info("result={}",bigDecimal);
    }

    @Test
    public void findPayTypeDetail(){
        String date = "2019-03-25";
        List<PayTypeDetailDTO> payTypeDetailDTOList = orderService.findPayTypeDetail(date,date);
        log.info("payTypeDetailDTOList={}",payTypeDetailDTOList);
        Assert.assertTrue(date+"支付情况表",payTypeDetailDTOList.size()>0);
    }

    @Test
    public void findTimeSaleDetail(){
        String date = "2019-03-25";
        List<TimeSaleDetailDTO> timeSaleDetailDTOList = orderService.findTimeSaleDetail(date,date);
        log.info("timeSaleDetailDTOList={}",timeSaleDetailDTOList);
    }

    @Test
    public void findProductSaleDetail(){
        String str = "2019-03-25";
        List<ProductSaleDetailDTO> productSaleDetailDTOList = orderService.findProductSaleDetail(str,str);
        log.info("productSaleDetailDTOList={}",productSaleDetailDTOList);
        Assert.assertTrue(str+"商品销售情况表",productSaleDetailDTOList.size()>0);
    }
}