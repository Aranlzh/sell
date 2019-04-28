package top.aranlzh.sell.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.aranlzh.sell.dataobject.SellerInfo;

import java.util.List;

/**
 * @author: Aranl
 * @date: 2019/3/12
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setPhone("13112178295");
        sellerInfo.setPassword("admin");

        SellerInfo result = repository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByPhone() throws Exception {
        List<SellerInfo> result = repository.findByPhone("13112178295");
        Assert.assertNotNull(result);
    }

}