package top.aranlzh.sell.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.aranlzh.sell.converter.OrderMaster2OrderDTOConverter;
import top.aranlzh.sell.dataobject.OrderDetail;
import top.aranlzh.sell.dataobject.OrderMaster;
import top.aranlzh.sell.dataobject.ProductInfo;
import top.aranlzh.sell.dto.*;
import top.aranlzh.sell.enums.OrderStatusEnum;
import top.aranlzh.sell.enums.PayStatusEnum;
import top.aranlzh.sell.enums.PayTypeEnum;
import top.aranlzh.sell.enums.ResultEnum;
import top.aranlzh.sell.exception.SellException;
import top.aranlzh.sell.mapper.OrderDetailMapper;
import top.aranlzh.sell.mapper.OrderMasterMapper;
import top.aranlzh.sell.repository.OrderDetailRepository;
import top.aranlzh.sell.repository.OrderMasterRepository;
import top.aranlzh.sell.service.OrderService;
import top.aranlzh.sell.service.ProductService;
import top.aranlzh.sell.service.WebSocket;
import top.aranlzh.sell.utils.DateUtil;
import top.aranlzh.sell.utils.KeyUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aranlzh
 * @create 2019-03-12 15:26
 * @desc
 **/
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1. 查询商品（数量, 价格）
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
            ProductInfo productInfo =  productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2. 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);// 把productInfo的属性拷贝到orderDetail
            orderDetailRepository.save(orderDetail);
        }


        //3. 写入订单数据库（orderMaster和orderDetail）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setPayStatus(PayTypeEnum.WAIT.getCode());
        log.info("orderMaster={}",orderMaster);
        orderMasterRepository.save(orderMaster);

        //4. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        //发送websocket消息
        webSocket.sendMessage("NEW",orderId);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public OrderMaster findOneByTableId(Integer tableId) {
        log.info("findOneByTableId");
        OrderMaster orderMaster = orderMasterRepository.findFirstByTableIdOrderByCreateTimeDesc(tableId);
        log.info("orderMaster={}",orderMaster);

        return orderMaster;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public void buyerCancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        BeanUtils.copyProperties(orderDTO, orderMaster);
        String orderId = orderMaster.getOrderId();

        //发送websocket消息
        webSocket.sendMessage("CNL",orderId);
    }

    @Override
    public void buyerUrge(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【催促订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        BeanUtils.copyProperties(orderDTO, orderMaster);
        String orderId = orderMaster.getOrderId();

        //发送websocket消息
        webSocket.sendMessage("URG",orderId);

    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }


        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (orderDTO.getOrderStatus().equals(OrderStatusEnum.CANCEL.getCode())) {
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    public Page<OrderDTO> findOneDayOrderList(String date, Pageable pageable) {
        Date startTime = DateUtil.getStartTime(date);
        Date endTime = DateUtil.getEndTime(date);
        log.info("startTime={},endTime={}",startTime,endTime);
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByCreateTimeBetween(startTime,endTime,pageable);
        log.info("TestForFindByCreateTimeBetween={}",orderMasterPage);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    public Page<OrderDTO> findOrderListByOrderStatus(Integer orderStatus, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByOrderStatusOrderByCreateTimeDesc(orderStatus, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    public Integer countByCreateTimeBetween(String startDate, String endDate) {
        Date startTime = DateUtil.getStartTime(startDate);
        Date endTime = DateUtil.getEndTime(endDate);
        return orderMasterRepository.countOrderByCreateTimeBetween(startTime,endTime);
    }

    @Override
    public BigDecimal sumOrderAmountByCreateTimeBetween(String startDate, String endDate) {
        Date startTime = DateUtil.getStartTime(startDate);
        Date endTime = DateUtil.getEndTime(endDate);
        return orderMasterRepository.sumOrderAmountByCreateTimeBetween(startTime,endTime);
    }

    @Override
    public List<PayTypeDetailDTO> findPayTypeDetail(String startDate, String endDate) {
        Date startTime = DateUtil.getStartTime(startDate);
        Date endTime = DateUtil.getEndTime(endDate);
        PayTypeDetailDTO payTypeDetailDTO = new PayTypeDetailDTO();
        payTypeDetailDTO.setStartTime(startTime);
        payTypeDetailDTO.setEndTime(endTime);
        return orderMasterMapper.findPayTypeDetailByDate(payTypeDetailDTO);
    }

    @Override
    public List<TimeSaleDetailDTO> findTimeSaleDetail(String startDate, String endDate) {
        Date startTime = DateUtil.getStartTime(startDate);
        Date endTime = DateUtil.getEndTime(endDate);
        TimeSaleDetailDTO timeSaleDetailDTO = new TimeSaleDetailDTO();
        timeSaleDetailDTO.setStartTime(startTime);
        timeSaleDetailDTO.setEndTime(endTime);
        return orderMasterMapper.findTimeSaleDetailByDate(timeSaleDetailDTO);
    }

    @Override
    public List<ProductSaleDetailDTO> findProductSaleDetail(String startDate, String endDate) {
        Date startTime = DateUtil.getStartTime(startDate);
        Date endTime = DateUtil.getEndTime(endDate);
        ProductSaleDetailDTO productSaleDetailDTO = new ProductSaleDetailDTO();
        productSaleDetailDTO.setStartTime(startTime);
        productSaleDetailDTO.setEndTime(endTime);
        return orderDetailMapper.findProcuctSaleDetailByDate(productSaleDetailDTO);
    }


}
