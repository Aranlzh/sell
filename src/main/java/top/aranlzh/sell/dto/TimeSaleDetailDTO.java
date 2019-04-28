package top.aranlzh.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.aranlzh.sell.enums.TimeFormatEnum;
import top.aranlzh.sell.utils.EnumUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Aranlzh
 * @create 2019-04-02 10:32
 * @desc 时间端销售情况
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSaleDetailDTO implements Serializable {
    private Integer hour;
    private Integer count;
    private Date startTime;
    private Date endTime;

    @JsonIgnore
    public TimeFormatEnum getTimeFormatEnum() {
        return EnumUtil.getByCode(hour, TimeFormatEnum.class);
    }
}
