package top.aranlzh.sell.service;

import top.aranlzh.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-03-11 22:01
 * @desc 类目
 **/
public interface CategoryService {
    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    ProductCategory save(ProductCategory productCategory);
}
