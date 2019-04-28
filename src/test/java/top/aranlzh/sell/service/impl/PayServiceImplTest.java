package top.aranlzh.sell.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.aranlzh.sell.dto.OrderDTO;
import top.aranlzh.sell.service.OrderService;
import top.aranlzh.sell.service.PayService;

/**
 * @author: Aranl
 * @date: 2019/3/13
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void pay() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1499097366838352541");
        payService.pay(orderDTO);
    }

}