package com.blogs.utils;

import com.blogs.common.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UploadUtil {
    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public static List<String> target = Arrays.asList("src", "main", "resources", "static", "public");
    public static List<String> imageSuffix = Arrays.asList("jpg", "jpeg", "png", "bmp", "tiff", "gif", "svg", "webp");

    // 头像上传目录
    public static String avatarSubdir = "avatar";
    // 头像支持的文件类型
    public static List<String> avatarSupport = Arrays.asList("jpg", "png");


    // 获取文件夹路径
    public static File createPublicPath() {
        String basePath = null;
        try {
            // 获取这个项目在系统里的绝对路径
            basePath = new File("").getCanonicalPath() + File.separator;

            // 这个项目的绝对路径 + target 目录(项目路径 +src/main/resources/static/public/)
            String path = basePath + String.join(File.separator, target) + File.separator;

            // 将path创建成文件夹的形式
            File fileDir = new File(path);
            if (!fileDir.exists()) {
                // 若没有则递归生成文件夹
                fileDir.mkdirs();
            }
            return fileDir;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 获取文件类型的路径
    public static String getTypePath(String suffix) {
        String path = "";
        if (imageSuffix.contains(suffix)) path = "images";
        if (path.equals("")) path = "others";
        return path;
    }

    // 获取文件后缀
    public static String getSuffix(String fileName) {

        // 若文件名为空，则返回空字符串
        if (fileName == null) return "";

        // 返回文件名的后缀
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    // 文件类型是否支持
    public static boolean isSupport(MultipartFile file, List<String> support) {
        String suffix = getSuffix(file.getOriginalFilename());
        return support.contains(suffix);
    }

    // 头像路径
    public static String avatarPath() {
        return "http://localhost:8081/resources/static/public/images/default.jpeg";

    }

    // 上传文件
    public static String upload(MultipartFile file) {
        if (file.isEmpty()) throw new ServiceException("文件为空");

        // 获取文件名
        String filename = file.getOriginalFilename();

        // 生成新的文件名
        String newName = UUID.randomUUID().toString().replace("-", "") + filename.substring(filename.lastIndexOf("."));

        // 获取文件夹路径
        String path = createPublicPath().getAbsolutePath();


        // 获取文件类型的路径
        String type = getTypePath(getSuffix(filename));
        String typePath = File.separator + type + File.separator;

        // 构建上传文件的存放路径 +D:\IdeaProjects\blogs\src\main\resources\static\public          +\images\        +1.jpg
        File dest = new File(path + typePath + newName);

        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            // 递归生成文件夹
            dest.getParentFile().mkdirs();
        }

        try {
            // 上传文件
            file.transferTo(dest);
            return type + "/" + newName;
        } catch (IOException e) {
            throw new ServiceException("上传失败");
        }
    }

}
