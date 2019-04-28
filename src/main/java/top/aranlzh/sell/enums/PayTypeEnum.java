package top.aranlzh.sell.enums;

import lombok.Getter;

/**
 * @author Aranlzh
 * @create 2019-03-12 15:01
 * @desc
 **/
@Getter
public enum PayTypeEnum implements CodeEnum {

    WAIT(0,"未支付"),
    CASH(1, "现金"),
    ALIPAY(2, "支付宝"),
    WECHAT(3,"微信"),
    ;

    private Integer code;

    private String message;

    PayTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
