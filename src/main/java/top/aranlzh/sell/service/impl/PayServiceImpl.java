package top.aranlzh.sell.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aranlzh.sell.dto.OrderDTO;
import top.aranlzh.sell.service.OrderService;
import top.aranlzh.sell.service.PayService;

/**
 * @author Aranlzh
 * @create 2019-03-12 16:19
 * @desc
 **/
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private OrderService orderService;

    @Override
    public void pay(OrderDTO orderDTO) {
        orderService.paid(orderDTO);
    }
}
