package top.aranlzh.sell.enums;

import lombok.Getter;

/**
 * @author Aranlzh
 * @create 2019-04-02 14:16
 * @desc
 **/
@Getter
public enum  TimeFormatEnum implements CodeEnum {
    ZERO(0,"00:00-01:00"),
    ONE(1, "01:00-02:00"),
    TWO(2, "02:00-03:00"),
    THREE(3,"03:00-04:00"),
    FOUR(4,"04:00-05:00"),
    FIVE(5,"05:00-06:00"),
    SIX(6,"06:00-07:00"),
    SEVEN(7,"07:00-08:00"),
    EIGHT(8,"08:00-09:00"),
    NINE(9,"09:00-10:00"),
    TEN(10,"10:00-11:00"),
    ELEVEN(11,"11:00-12:00"),
    TWELVE(12,"12:00-13:00"),
    THIRTEEN(13,"13:00-14:00"),
    FOURTEEN(14,"14:00-15:00"),
    FIFTEEN(15,"15:00-16:00"),
    SIXTEEN(16,"16:00-17:00"),
    SEVENTEEN(17,"17:00-18:00"),
    EIGHTEEN(18,"18:00-19:00"),
    NINETEEN(19,"19:00-20:00"),
    TWENTY(20,"20:00-21:00"),
    TWENTY_ONE(21,"21:00-22:00"),
    TWENTY_TWO(22,"22:00-23:00"),
    TWENTY_THREE(23,"23:00-00:00"),
    ;

    private Integer code;

    private String message;

    TimeFormatEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
