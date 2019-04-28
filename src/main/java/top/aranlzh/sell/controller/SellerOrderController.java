package top.aranlzh.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import top.aranlzh.sell.dto.OrderDTO;
import top.aranlzh.sell.dto.ReportDetail;
import top.aranlzh.sell.enums.ResultEnum;
import top.aranlzh.sell.exception.SellException;
import top.aranlzh.sell.service.OrderService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Aranlzh
 * @create 2019-03-13 15:34
 * @desc 卖家端订单
 **/
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    private Date date = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String today = sdf.format(date);
    /**
     * 订单列表
     * @param page 第几页, 从1页开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest request = new PageRequest(page - 1, size, sort);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    @GetMapping("/dateList")
    public ModelAndView dateList(@RequestParam("date") String date,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                 Map<String, Object> map) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest request = new PageRequest(page - 1, size, sort);
        Page<OrderDTO> orderDTOPage = orderService.findOneDayOrderList(date,request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
//        orderDTOPage.getTotalPages()
        return new ModelAndView("order/list", map);
    }

    @GetMapping("/listByOrderStatus")
    public ModelAndView listByOrderStatus(@RequestParam("orderStatus") Integer orderStatus,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                 Map<String, Object> map) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest request = new PageRequest(page - 1, size, sort);
        Page<OrderDTO> orderDTOPage = orderService.findOrderListByOrderStatus(orderStatus,request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
//        orderDTOPage.getTotalPages()
        return new ModelAndView("order/list", map);
    }

    /**
     * 支付订单
     */
    @GetMapping("/paid")
    public ModelAndView paid(@RequestParam("orderId") String orderId,
                             Map<String, Object> map){
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
        }catch (SellException e) {
            log.error("【支付订单：订单状态错误】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/dateList?date="+today);
            return new ModelAndView("common/error", map);
        }
        map.put("orderDTO", orderDTO);

        return new ModelAndView("pay/index",map);
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端取消订单】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/dateList?date="+today);
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/dateList?date="+today);
        return new ModelAndView("common/success");
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
        }catch (SellException e) {
            log.error("【卖家端查询订单详情】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/dateList?date="+today);
            return new ModelAndView("common/error", map);
        }

        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }

    @GetMapping("/report")
    public ModelAndView dayReport(@RequestParam("startDate") String startDate,
                                    @RequestParam("endDate") String endDate,
                                  Map<String, Object> map){
        ReportDetail reportDetail = new ReportDetail();
        Integer num = orderService.countByCreateTimeBetween(startDate,endDate);
        if (num==0){
            reportDetail.setReportNum(num);
        }else{
            reportDetail.setReportNum(num);
            reportDetail.setSaleAmount(orderService.sumOrderAmountByCreateTimeBetween(startDate,endDate));
            reportDetail.setPayTypeDetailDTOList(orderService.findPayTypeDetail(startDate,endDate));
            reportDetail.setTimeSaleDetailDTOList(orderService.findTimeSaleDetail(startDate,endDate));
            reportDetail.setProductSaleDetailDTOList(orderService.findProductSaleDetail(startDate,endDate));
        }
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("reportDetail",reportDetail);
        return new ModelAndView("order/report",map);
    }

//    @GetMapping("/rangeReport")
//    public ModelAndView rangeReport(@RequestParam("startDate") String startDate,
//                                    @RequestParam("endDate") String endDate,
//                                  Map<String, Object> map){
//        ReportDetail reportDetail = new ReportDetail();
//        Integer num = orderService.countByCreateTimeBetween(startDate,endDate);
//        if (num==0){
//            reportDetail.setReportNum(num);
//        }else{
//            reportDetail.setReportNum(num);
//            reportDetail.setSaleAmount(orderService.sumOrderAmountByCreateTimeBetween(startDate,endDate));
//            reportDetail.setPayTypeDetailDTOList(orderService.findPayTypeDetail(startDate,endDate));
//            reportDetail.setTimeSaleDetailDTOList(orderService.findTimeSaleDetail(startDate,endDate));
//            reportDetail.setProductSaleDetailDTOList(orderService.findProductSaleDetail(startDate,endDate));
//        }
//        map.put("startDate",startDate);
//        map.put("endDate",endDate);
//        map.put("reportDetail",reportDetail);
//        return new ModelAndView("order/report",map);
//    }

    /**
     * 完结订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId") String orderId,
                                 Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端完结订单】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/dateList?date="+today);
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/dateList?date="+today);
        return new ModelAndView("common/success");
    }
}
