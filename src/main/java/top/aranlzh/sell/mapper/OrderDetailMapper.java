package top.aranlzh.sell.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.aranlzh.sell.dto.ProductSaleDetailDTO;

import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-04-01 15:38
 * @desc
 **/
@Mapper
public interface OrderDetailMapper {
    List<ProductSaleDetailDTO> findProcuctSaleDetailByDate(ProductSaleDetailDTO productSaleDetailDTO);
}
