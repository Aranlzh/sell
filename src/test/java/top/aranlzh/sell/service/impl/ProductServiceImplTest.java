package top.aranlzh.sell.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import top.aranlzh.sell.dataobject.ProductInfo;
import top.aranlzh.sell.enums.ProductStatusEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: Aranl
 * @date: 2019/3/12
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() throws Exception{
        ProductInfo productInfo = productService.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findUpAll() throws Exception{
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() throws  Exception{
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> productInfoList = productService.findAll(request);
        Assert.assertNotEquals(0,productInfoList.getTotalElements());
    }

    @Test
    public void save() throws Exception{
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("234567").setProductName("瘦肉肠").setProductPrice(new BigDecimal(5.0)).setProductStock(100).setProductDescription("广州最基本的拉肠之一").setProductIcon("http://xxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode()).setCategoryType(2);
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }

//    @Test
//    public void  searchByKey(){
//        String key = "肠";
//        PageRequest request = new PageRequest(0,2);
//        Page<ProductInfo> productInfoPage = productService.findProductByKey(key,request);
//        log.info("productInfoPage={}",productInfoPage);
//    }


}