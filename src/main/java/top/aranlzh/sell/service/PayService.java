package top.aranlzh.sell.service;

import top.aranlzh.sell.dto.OrderDTO;

/**
 * @author Aranlzh
 * @create 2019-03-12 16:18
 * @desc 支付
 **/
public interface PayService {
    void pay(OrderDTO orderDTO);
}
