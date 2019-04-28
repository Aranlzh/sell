package top.aranlzh.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.aranlzh.sell.enums.PayTypeEnum;
import top.aranlzh.sell.utils.EnumUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Aranlzh
 * @create 2019-04-02 9:33
 * @desc 各支付方式的收款数
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayTypeDetailDTO implements Serializable {
    private Integer payType;
    private BigDecimal sum;
    private Date startTime;
    private Date endTime;

    @JsonIgnore
    public PayTypeEnum getPayTypesEnum() {
        return EnumUtil.getByCode(payType, PayTypeEnum.class);
    }
}
