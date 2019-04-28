package top.aranlzh.sell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.aranlzh.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-03-11 21:01
 * @desc
 **/
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    /** 通过类目编号查找 */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
