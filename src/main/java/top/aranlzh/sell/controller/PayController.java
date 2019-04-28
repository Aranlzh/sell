package top.aranlzh.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.aranlzh.sell.dto.OrderDTO;
import top.aranlzh.sell.enums.ResultEnum;
import top.aranlzh.sell.exception.SellException;
import top.aranlzh.sell.form.PayTypeForm;
import top.aranlzh.sell.service.OrderService;
import top.aranlzh.sell.service.PayService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Aranlzh
 * @create 2019-03-13 15:36
 * @desc 支付
 **/
@Controller
@RequestMapping("/seller/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    private Date date = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String today = sdf.format(date);

    /**
     * 支付订单
     */
    @PostMapping("/paid")
    public ModelAndView paid(@Valid PayTypeForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map){
//        log.info("form={}",form);
//        log.info("orderId={},payType={}",form.getOrderId(),form.getPayType());
        // 1.查询订单
        OrderDTO orderDTO = orderService.findOne(form.getOrderId());
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2. 设置订单支付方式
        orderDTO.setPayType(form.getPayType());
//        log.info("payTypesEnum={}",orderDTO.getPayTypesEnum().getMessage());
        payService.pay(orderDTO);

        map.put("msg","支付成功！支付方式为："+orderDTO.getPayTypesEnum().getMessage());
        map.put("url", "/sell/seller/order/dateList?date="+today);
        return new ModelAndView("common/success",map);
    }
}
