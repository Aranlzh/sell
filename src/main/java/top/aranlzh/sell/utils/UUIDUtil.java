package top.aranlzh.sell.utils;

import java.util.UUID;

/**
 * @author Aranlzh
 * @create 2019-04-11 11:15
 * @desc 生成文件名
 **/
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
