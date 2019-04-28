package top.aranlzh.sell.enums;

import lombok.Getter;

/**
 * @author Aranlzh
 * @create 2019-03-11 22:22
 * @desc
 **/
@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(0, "在架"),
    DOWN(1, "下架")
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
