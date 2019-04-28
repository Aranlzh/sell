package top.aranlzh.sell.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import top.aranlzh.sell.dataobject.OrderMaster;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Aranlzh
 * @create 2019-03-12 15:22
 * @desc
 **/
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String>, CrudRepository<OrderMaster, String> {
    @Query(value = "SELECT COUNT(*) FROM order_master WHERE create_time BETWEEN :startTime AND :endTime AND order_status!=2",nativeQuery = true)
    Integer countOrderByCreateTimeBetween(@Param("startTime") Date start, @Param("endTime") Date end);
    @Query(value = "SELECT sum( order_amount ) FROM order_master WHERE create_time BETWEEN :startTime AND :endTime AND order_status!=2", nativeQuery = true)
    BigDecimal sumOrderAmountByCreateTimeBetween(@Param("startTime") Date start, @Param("endTime") Date end);
    OrderMaster findFirstByTableIdOrderByCreateTimeDesc(Integer tableId);
    Page<OrderMaster> findByCreateTimeBetween(Date start, Date end, Pageable pageable);
    Page<OrderMaster> findByOrderStatusOrderByCreateTimeDesc(Integer orderStatus, Pageable pageable);
}
