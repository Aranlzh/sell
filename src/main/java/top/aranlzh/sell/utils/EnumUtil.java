package top.aranlzh.sell.utils;

import top.aranlzh.sell.enums.CodeEnum;

/**
 * @author Aranlzh
 * @create 2019-03-11 22:23
 * @desc
 **/
public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}