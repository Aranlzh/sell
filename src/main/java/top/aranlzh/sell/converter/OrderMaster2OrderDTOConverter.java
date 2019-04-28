package top.aranlzh.sell.converter;

import org.springframework.beans.BeanUtils;
import top.aranlzh.sell.dataobject.OrderMaster;
import top.aranlzh.sell.dto.OrderDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aranlzh
 * @create 2019-03-12 16:45
 * @desc
 **/
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster) {

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }
}
