package top.aranlzh.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.aranlzh.sell.VO.ResultVO;
import top.aranlzh.sell.converter.OrderForm2OrderDTOConverter;
import top.aranlzh.sell.dataobject.OrderMaster;
import top.aranlzh.sell.dto.OrderDTO;
import top.aranlzh.sell.enums.ResultEnum;
import top.aranlzh.sell.exception.SellException;
import top.aranlzh.sell.form.OrderForm;
import top.aranlzh.sell.service.OrderService;
import top.aranlzh.sell.utils.ResultVOUtil;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aranlzh
 * @create 2019-03-13 15:25
 * @desc
 **/
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    // 查询订单
    @GetMapping("/find")
    public ResultVO<Map<String,String>> find(@Valid Integer tableId){
        log.info("tableId={}",tableId);

        OrderMaster orderMaster = orderService.findOneByTableId(tableId);
        if (orderMaster==null){
            return ResultVOUtil.error(2,"当前桌子没有订单！");
        }
        if (orderMaster.getOrderStatus()==0){
            Map<String, String> map = new HashMap<>();
            map.put("orderId", orderMaster.getOrderId());

            return ResultVOUtil.success(map);
        }
        return ResultVOUtil.error(1,"当前桌子没有未完成订单！");
    }

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        log.info("orderForm={}",orderForm);
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(map);
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("orderId") String orderId) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.buyerCancel(orderDTO);
        } catch (SellException e) {
            log.error("【买家申请取消订单】发生异常{}", e);
            return ResultVOUtil.error(2,"【取消订单】发生异常"+e);
        }
        return ResultVOUtil.error(1,"等待商家确认...");
    }

    //催促订单
    @PostMapping("/urge")
    public ResultVO urge(@RequestParam("orderId") String orderId) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.buyerUrge(orderDTO);
        } catch (SellException e) {
            log.error("【买家催促订单】发生异常{}", e);
            return ResultVOUtil.error(2,"【催促订单】发生异常"+e);
        }
        return ResultVOUtil.success("已经成功催促啦！");
    }

}