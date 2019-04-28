package top.aranlzh.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import top.aranlzh.sell.dataobject.OrderDetail;
import top.aranlzh.sell.dto.OrderDTO;
import top.aranlzh.sell.enums.ResultEnum;
import top.aranlzh.sell.exception.SellException;
import top.aranlzh.sell.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-03-13 11:39
 * @desc
 **/
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setTableId(orderForm.getTableId());

        orderDTO.setEatType(orderForm.getEatType());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        log.info("orderDTO={}",orderDTO);
        return orderDTO;
    }
}
