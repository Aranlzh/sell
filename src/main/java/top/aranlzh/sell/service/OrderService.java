package top.aranlzh.sell.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.aranlzh.sell.dataobject.OrderMaster;
import top.aranlzh.sell.dto.OrderDTO;
import top.aranlzh.sell.dto.PayTypeDetailDTO;
import top.aranlzh.sell.dto.ProductSaleDetailDTO;
import top.aranlzh.sell.dto.TimeSaleDetailDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-03-12 15:23
 * @desc
 **/
public interface OrderService {

    /** 创建订单. */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单. */
    OrderDTO findOne(String orderId);

    /** 查看当前桌子有没有未完成的订单. */
    OrderMaster findOneByTableId(Integer tableId);

    /** 取消订单. */
    OrderDTO cancel(OrderDTO orderDTO);
    /** 顾客取消订单. */
    void buyerCancel(OrderDTO orderDTO);
    /** 顾客催促订单. */
    void buyerUrge(OrderDTO orderDTO);

    /** 完结订单. */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单. */
    OrderDTO paid(OrderDTO orderDTO);

    /** 查询订单列表. */
    Page<OrderDTO> findList(Pageable pageable);

    /** 查询某一天的订单. */
    Page<OrderDTO> findOneDayOrderList(String date, Pageable pageable);

    /** 根据订单状态查询. */
    Page<OrderDTO> findOrderListByOrderStatus(Integer orderStatus, Pageable pageable);

    /** 订单数. */
    Integer countByCreateTimeBetween(String startDate, String endDate);

    /** 营业额. */
    BigDecimal sumOrderAmountByCreateTimeBetween(String startDate, String endDate);

    /** 各收款方式的额度. */
    List<PayTypeDetailDTO> findPayTypeDetail(String startDate, String endDate);

    /** 销售情况. */
    List<TimeSaleDetailDTO> findTimeSaleDetail(String startDate, String endDate);

    /** 商品销售情况. */
    List<ProductSaleDetailDTO> findProductSaleDetail(String startDate, String endDate);


}
