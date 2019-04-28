package top.aranlzh.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import top.aranlzh.sell.dataobject.OrderDetail;
import top.aranlzh.sell.enums.EatTypeEnum;
import top.aranlzh.sell.enums.OrderStatusEnum;
import top.aranlzh.sell.enums.PayStatusEnum;
import top.aranlzh.sell.enums.PayTypeEnum;
import top.aranlzh.sell.utils.EnumUtil;
import top.aranlzh.sell.utils.serializer.Date2LongSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-03-12 15:24
 * @desc
 **/
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /** 订单id. */
    private String orderId;

    /** 桌号. */
    private Integer tableId;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0堂食. */
    private Integer eatType;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付方式, 默认为0未支付 */
    private Integer payStatus;

    /** 支付方式, 默认为0未支付，1现金 2支付宝 3微信. */
    private Integer payType;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public EatTypeEnum getEatTypeEnum() {
        return EnumUtil.getByCode(eatType, EatTypeEnum.class);
    }

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }

    @JsonIgnore
    public PayTypeEnum getPayTypesEnum() {
        return EnumUtil.getByCode(payType, PayTypeEnum.class);
    }
}
