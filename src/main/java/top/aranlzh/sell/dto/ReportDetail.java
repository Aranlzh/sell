package top.aranlzh.sell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-03-27 19:04
 * @desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDetail {

    /** 订单数. */
    private Integer reportNum;

    /** 营业额. */
    private BigDecimal saleAmount;

    /** 支付详情. */
    private List<PayTypeDetailDTO> payTypeDetailDTOList;

    /** 时间段销售情况. */
    private List<TimeSaleDetailDTO> timeSaleDetailDTOList;

    /** 商品销售情况. */
    private List<ProductSaleDetailDTO> productSaleDetailDTOList;
}
