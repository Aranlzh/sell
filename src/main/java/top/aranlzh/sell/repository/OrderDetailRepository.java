package top.aranlzh.sell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.aranlzh.sell.dataobject.OrderDetail;

import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-03-12 15:23
 * @desc
 **/
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    List<OrderDetail> findByOrderId(String orderId);

//    @Query(value = "SELECT a.product_name, COUNT(a.product_name) FROM order_detail a WHERE a.create_time BETWEEN :startTime AND :endTime GROUP BY a.product_name")
//    List<Object[]> findProductSaleDetail(@Param("startTime") Date start, @Param("endTime") Date end);
}
