package top.aranlzh.sell.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.aranlzh.sell.dataobject.OrderMaster;

import java.math.BigDecimal;

/**
 * @author: Aranl
 * @date: 2019/3/12
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "201521314419";

    @Test
    public void saveTest() throws Exception{
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");;
        orderMaster.setTableId(3);
        orderMaster.setOrderAmount(new BigDecimal(3.0));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }


//    @Test
//    public void findByBuyerOpenid() throws Exception{
//        PageRequest request = new PageRequest(0,1);
//        Page<OrderMaster> result =  repository.findByBuyerOpenid(OPENID,request);
//        Assert.assertNotEquals(0,result.getTotalElements());
//    }

}