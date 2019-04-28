package top.aranlzh.sell.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.aranlzh.sell.dataobject.ProductInfo;
import top.aranlzh.sell.dto.CartDTO;

import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-03-12 0:03
 * @desc 商品
 **/
public interface ProductService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);

    // 查找商品
    Page<ProductInfo> findProductByKey(String productNameKey, Pageable pageable);

    List<ProductInfo> findUpProductByKey(String productNameKey);
}
