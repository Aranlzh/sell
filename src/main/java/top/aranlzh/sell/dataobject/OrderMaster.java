package top.aranlzh.sell.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import top.aranlzh.sell.enums.OrderStatusEnum;
import top.aranlzh.sell.enums.PayStatusEnum;
import top.aranlzh.sell.enums.PayTypeEnum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Aranlzh
 * @create 2019-03-12 14:54
 * @desc 订单表
 **/
@Entity
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderMaster {

    /** 订单id */
    @Id
    private String orderId;

    /** 桌号 */
    private Integer tableId;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 就餐方式 */
    private Integer eatType;

    /** 订单状态 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态 */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 支付方式 */
    private Integer payType = PayTypeEnum.WAIT.getCode();

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}
