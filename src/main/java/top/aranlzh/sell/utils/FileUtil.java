package top.aranlzh.sell.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Aranlzh
 * @create 2019-04-11 11:11
 * @desc 文件上传工具包
 **/
public class FileUtil {
    /**
     * @param file 文件
     * @param path 文件存放路径
     * @param fileName 源文件名
     */
    public static String upload(MultipartFile file, String path, String fileName){
        // 生成新的文件名
        String newFileName = FileNameUtil.getFileName(fileName);
         String realPath = path + "/" + newFileName;

        // 使用原文件名
        // String realPath = path + "/" + fileName;
        File dest = new File(realPath);
        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return newFileName;
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
