package top.aranlzh.sell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.aranlzh.sell.VO.ResultVO;
import top.aranlzh.sell.utils.FileUtil;
import top.aranlzh.sell.utils.ResultVOUtil;

import java.io.File;
import java.util.Map;

/**
 * @author Aranlzh
 * @create 2019-04-11 11:43
 * @desc 文件上传
 **/
@Controller
@RestController
@RequestMapping("/file")
public class FileController {

    private final ResourceLoader resourceLoader;

    @Autowired
    public FileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /* 获取项目路径 */
    private static String property = System.getProperty("user.dir");
    private static File file3 = new File(property);
//    private static String filePath = file3.getPath() + File.separator + "src\\main\\resources\\static\\img";
    private static String filePath = "/root/jars/static/img/";

    @PostMapping(value="/uploadImg")
    public ResultVO<Map<String,String>> uploadImg(@RequestParam("uploadImgInput") MultipartFile file){
        // 上传成功或者失败的提示
        String newFileName = FileUtil.upload(file, filePath, file.getOriginalFilename());
        if (!newFileName.isEmpty()){
            return ResultVOUtil.success(newFileName);
        }else {
            return ResultVOUtil.error(1,"上传失败！");
        }
    }

    @GetMapping(value="/show")
    public ResponseEntity showPhotos(@RequestParam("productIcon") String productIcon){
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + filePath + File.separator + productIcon));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping(value ="/upload")
//    public void fileUpLoad(@RequestParam(value = "file", required = true) MultipartFile file, Map<String, Object> map) {
//        // 上传文件并获得文件名
//        String newFileName = FileUtil.upload(file, filePath, file.getOriginalFilename());
//        // 上传成功或者失败的提示
//        String msg = "";
//        if (!newFileName.isEmpty()){
//            // 上传成功，给出页面提示
//            msg = "上传成功！";
//        }else {
//            msg = "上传失败！";
//        }
//        // 显示图片
//        map.put("msg", msg);
//        map.put("fileName", file.getOriginalFilename());
//
//    }

//    @GetMapping(value="/download")
//    public ResponseEntity<byte[]> download(@RequestParam("fileName") String fileName) throws IOException {
//        @SuppressWarnings("resource")
//        InputStream in = new FileInputStream(new File(filePath));// 将该文件加入到输入流之中
//        byte[] body = null;
//        body = new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
//        in.read(body);// 读入到输入流里面
//
//        fileName = new String(fileName.getBytes("gbk"), "iso8859-1");// 防止中文乱码
//        HttpHeaders headers = new HttpHeaders();// 设置响应头
//        headers.add("Content-Disposition", "attachment;filename=" + fileName);
//        HttpStatus statusCode = HttpStatus.OK;// 设置响应吗
//        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
//        return response;
//    }
}
