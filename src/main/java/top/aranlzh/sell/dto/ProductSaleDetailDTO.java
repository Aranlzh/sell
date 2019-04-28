package top.aranlzh.sell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Aranlzh
 * @create 2019-03-27 19:33
 * @desc 商品销售详情
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaleDetailDTO implements Serializable {
    private String productName;
    private Integer count;
    private Date startTime;
    private Date endTime;
}
