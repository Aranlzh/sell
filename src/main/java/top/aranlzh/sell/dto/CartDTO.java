package top.aranlzh.sell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Aranlzh
 * @create 2019-03-12 0:05
 * @desc 购物车
 **/
@Data
@AllArgsConstructor
public class CartDTO {
    /** 商品Id */
    private String productId;

    /** 数量 */
    private Integer productQuantity;
}