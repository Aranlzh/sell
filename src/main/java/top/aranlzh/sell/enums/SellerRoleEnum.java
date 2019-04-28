package top.aranlzh.sell.enums;

import lombok.Getter;

/**
 * @author Aranlzh
 * @create 2019-04-02 19:56
 * @desc
 **/
@Getter
public enum SellerRoleEnum implements CodeEnum{
    SUPER(0, "超级管理员"),
    ORDINARY(1, "普通管理员"),
    ;

    private Integer code;

    private String message;

    SellerRoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}