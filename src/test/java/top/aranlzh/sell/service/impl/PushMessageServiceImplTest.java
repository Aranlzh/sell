//package top.aranlzh.sell.service.impl;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import top.aranlzh.sell.dto.OrderDTO;
//import top.aranlzh.sell.service.OrderService;
//
///**
// * @author: Aranl
// * @date: 2019/3/13
// * @Description:
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class PushMessageServiceImplTest {
//    @Autowired
//    private PushMessageServiceImpl pushMessageService;
//
//    @Autowired
//    private OrderService orderService;
//
//    @Test
//    public void orderStatus() throws Exception {
//
//        OrderDTO orderDTO = orderService.findOne("1499097346204378750");
//        pushMessageService.orderStatus(orderDTO);
//    }
//}