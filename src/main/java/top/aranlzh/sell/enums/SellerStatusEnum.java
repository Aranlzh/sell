package top.aranlzh.sell.enums;

import lombok.Getter;

/**
 * @author Aranlzh
 * @create 2019-03-19 21:05
 * @desc 管理员状态
 **/
@Getter
public enum SellerStatusEnum implements CodeEnum{
    STOP(0, "已停用"),
    USE(1, "使用中"),
    ;

    private Integer code;

    private String message;

    SellerStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
