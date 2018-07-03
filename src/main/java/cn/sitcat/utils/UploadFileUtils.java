package cn.sitcat.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Hiseico
 * @description: 文件上传工具类
 * @date 2018/4/1012:42
 */

public class UploadFileUtils {
    public static String uploadFile(MultipartFile uploadFile,String fileSavePath) throws IOException {
        // 获取 文件名
        String realFileName = uploadFile.getOriginalFilename();
        // ext 为 文件扩展名
        String ext = realFileName.substring(realFileName.lastIndexOf(".") + 1);
        // 随机文件名
        String randomName = UUIDUtils.getUUID();
        // 拼接 文件名 与 扩展名
        String saveFileName = randomName + "." + ext;

        // 获取 文件流
        InputStream inputStream = uploadFile.getInputStream();
        // 创建 文件保存目录
        //已日期为目录形式保存
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy\\MM\\dd\\");
        String dateStr = simpleDateFormat.format(date);
        fileSavePath=fileSavePath+dateStr;
        File file = new File(fileSavePath);
        if (!file.exists()) {   // 若存储文件目录不存在则 进行创建
            file.mkdirs();
        }
        // 写入磁盘
        FileUtils.copyInputStreamToFile(inputStream, new File(file + "/" + saveFileName));

        // 返回 文件的 存储路径
        return fileSavePath + saveFileName;
    }
}
