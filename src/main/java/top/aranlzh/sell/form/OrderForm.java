package top.aranlzh.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Aranlzh
 * @create 2019-03-13 11:40
 * @desc
 **/
@Data
public class OrderForm {

    /**
     * 桌号
     */
    @NotNull(message = "桌号必填")
    private Integer tableId;

    /**
     * 就餐方式
     */
    @NotNull(message = "就餐方式必选")
    private Integer eatType;

    /**
     * 购物车
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;
}