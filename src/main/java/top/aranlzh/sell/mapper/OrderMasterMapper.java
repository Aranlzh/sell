package top.aranlzh.sell.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.aranlzh.sell.dto.PayTypeDetailDTO;
import top.aranlzh.sell.dto.TimeSaleDetailDTO;

import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-04-01 19:17
 * @desc
 **/
@Mapper
public interface OrderMasterMapper {
    List<PayTypeDetailDTO> findPayTypeDetailByDate(PayTypeDetailDTO payTypeDetailDTO);
    List<TimeSaleDetailDTO> findTimeSaleDetailByDate(TimeSaleDetailDTO timeSaleDetailDTO);
}
