package top.aranlzh.sell.enums;

import lombok.Getter;

/**
 * @author: Aranl
 * @date: 2019/3/31
 * @Description: 就餐方式
 */
@Getter
public enum EatTypeEnum implements CodeEnum {
    DINE(0,"堂食"),
    TACKOUT(1, "外带"),
    ;

    private Integer code;

    private String message;

    EatTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
