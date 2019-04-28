package top.aranlzh.sell.form;

import lombok.Data;

/**
 * @author Aranlzh
 * @create 2019-03-25 16:35
 * @desc 支付方式
 **/
@Data
public class PayTypeForm {
    /** 订单号. */
    private String orderId;
    /** 支付方式. */
    private Integer payType;
}
