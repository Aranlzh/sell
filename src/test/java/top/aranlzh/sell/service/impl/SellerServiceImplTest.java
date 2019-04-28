package top.aranlzh.sell.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import top.aranlzh.sell.dataobject.SellerInfo;
import top.aranlzh.sell.enums.SellerStatusEnum;

import java.util.List;

/**
 * @author: Aranl
 * @date: 2019/3/19
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerServiceImplTest {

    private static final String phone="13112178295";
    private static final String password="admin";

    @Autowired
    private SellerServiceImpl sellerService;
    @Test
    public void findSellerInfoByUsernameAndPassword() throws Exception {
        SellerInfo result = sellerService.findSellerInfoByPhoneAndPassword(phone,password);
        Assert.assertNotNull(result);
    }

    @Test
    public void stop() throws Exception {
        SellerInfo sellerInfo = sellerService.findOneByPhone(phone);
        SellerInfo result = sellerService.stop(sellerInfo);
        Assert.assertEquals(SellerStatusEnum.STOP.getCode(), result.getStatus());
    }

    @Test
    public void use() throws Exception {
        SellerInfo sellerInfo = sellerService.findOneByPhone(phone);
        SellerInfo result = sellerService.use(sellerInfo);
        Assert.assertEquals(SellerStatusEnum.USE.getCode(), result.getStatus());
    }

    @Test
    public void list() {
        PageRequest request = new PageRequest(0,2);
        List<SellerInfo> sellerInfoList = sellerService.findList();
        Assert.assertTrue("查询所有的订单列表", sellerInfoList.size()>0);
    }

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo("13602289267","test",1,1);
        SellerInfo result = sellerService.save(sellerInfo);
        Assert.assertNotNull(result);
    }
}