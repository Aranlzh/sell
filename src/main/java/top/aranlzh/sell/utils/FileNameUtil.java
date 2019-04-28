package top.aranlzh.sell.utils;
/**
 * @author Aranlzh
 * @create 2019-04-11 11:12
 * @desc 文件上传重命名
 **/
public class FileNameUtil {
    // 获得文件后缀
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    // 生成新文件名
    public static String getFileName(String fileOriginName){
        return UUIDUtil.getUUID() + FileNameUtil.getSuffix(fileOriginName);
    }
}
