package top.aranlzh.sell.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import top.aranlzh.sell.dataobject.ProductInfo;

import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-03-11 23:39
 * @desc
 **/
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    List<ProductInfo> findByProductStatus(Integer productStatus);
    List<ProductInfo> findByProductStatusAndProductNameLike(Integer productStatus, String productNameKey);
    Page<ProductInfo> findByProductNameLike(String productNameKey, Pageable pageable);
}
