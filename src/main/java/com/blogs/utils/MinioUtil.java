package com.blogs.utils;

import cn.hutool.core.io.FastByteArrayOutputStream;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.blogs.common.config.MinioConfig;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.blogs.common.global.GlobalConstants.DEFAULT_PART_SIZE;

@Component
@Slf4j
public class MinioUtil {
  @Resource
  private MinioConfig prop;

  @Resource
  private MinioClient minioClient;

  /**
   * 查看存储bucket是否存在
   *
   * @return boolean
   */
  public Boolean bucketExists(String bucketName) {
    Boolean found;
    try {
      found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return found;
  }

  /**
   * 创建存储bucket
   *
   * @return Boolean
   */
  public Boolean makeBucket(String bucketName) {
    try {
      minioClient.makeBucket(MakeBucketArgs.builder()
          .bucket(bucketName)
          .build());
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * 删除存储bucket
   *
   * @return Boolean
   */
  public Boolean removeBucket(String bucketName) {
    try {
      minioClient.removeBucket(RemoveBucketArgs.builder()
          .bucket(bucketName)
          .build());
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * 获取全部bucket
   */
  public List<String> getAllBuckets() {
    try {
      // 获取所有桶信息
      List<Bucket> buckets = minioClient.listBuckets();
      List<String> bucketNames = new ArrayList<>();
      for (Bucket bucket : buckets) {
        bucketNames.add(bucket.name());
      }
      // 返回桶信息
      return bucketNames;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  /**
   *
   * 头像
   * @return Boolean
   */
  public String uploadAvatar(MultipartFile file) {
    // 获取上传的文件原始名称
    String originalFilename = file.getOriginalFilename();
    // 如果文件原始名称为空或者空白，则抛出运行时异常
    if (StringUtils.isBlank(originalFilename)) {
      throw new RuntimeException();
    }

    // 检查文件后缀是否是允许的图片格式
    String[] allowedExtensions = {".jpg", ".jpeg", ".png"};
    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
    boolean isAllowed = false;
    for (String ext : allowedExtensions) {
      if (fileExtension.equals(ext)) {
        isAllowed = true;
        break;
      }
    }
    // ============通用=================
    // 生成唯一的文件名部分，使用UUID生成一个唯一标识
    String uniqueFileNamePart = UUID.randomUUID().toString();
    // 获取文件的扩展名部分
    String fileNameExtension = isAllowed ? ".jpg" : fileExtension; // 统一保存为jpg格式
    String fileName = uniqueFileNamePart + fileNameExtension;

    // 使用 SimpleDateFormat 生成日期部分，格式为"yyyy-MM/dd"
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM/dd");
    // 获取当前日期的格式化字符串
    String datePart = dateFormat.format(new Date());
    // 组合日期部分和文件名，得到完整的对象名称（路径）
    String objectName = datePart + "/" + fileName;
    // ============通用=================

    try {
      BufferedImage originalImage = ImageIO.read(file.getInputStream());

      // 压缩图片（仅针对允许的图片格式）
      BufferedImage compressedImage = isAllowed ? compressImage(originalImage) : originalImage;

      // 获取压缩后的图片的字节数据
      byte[] compressedImageBytes = convertToByteArray(compressedImage);

      // 计算对象的大小
      long objectSize = compressedImageBytes.length;
      // 设置块大小
      long partSize = DEFAULT_PART_SIZE;
      // 构建上传参数对象
      PutObjectArgs objectArgs = PutObjectArgs.builder()
          .bucket(prop.getBucketName())
          .object(objectName)
          .stream(new ByteArrayInputStream(compressedImageBytes), objectSize, partSize)
          .contentType(file.getContentType())
          .build();

      // 使用 MinIO 客户端上传文件到指定对象路径
      minioClient.putObject(objectArgs);
    } catch (Exception e) {
      // 如果上传过程中发生异常，打印异常信息
      e.printStackTrace();
      // 返回 null 表示上传失败
      return null;
    }
    return objectName;
  }
    private BufferedImage compressImage(BufferedImage originalImage) {
      int newWidth = originalImage.getWidth() / 2;
      int newHeight = originalImage.getHeight() / 2;

      BufferedImage compressedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = compressedImage.createGraphics();
      g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
      g.dispose();

      return compressedImage;
    }

    private byte[] convertToByteArray(BufferedImage image) throws IOException {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageIO.write(image, "jpg", os);
      return os.toByteArray();
    }

/**
 *
 */
public String upload(MultipartFile file) {
  // 获取上传的文件原始名称
  String originalFilename = file.getOriginalFilename();
  // 如果文件原始名称为空或者空白，则抛出运行时异常
  if (StringUtils.isBlank(originalFilename)) {
    throw new RuntimeException();
  }

  // ============通用=================
  // 生成唯一的文件名部分，使用UUID生成一个唯一标识
  String uniqueFileNamePart = UUID.randomUUID().toString();
  // 获取文件的扩展名部分
  String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
  // 组合唯一文件名部分和扩展名，得到完整的文件名
  String fileName = uniqueFileNamePart + fileExtension;

  // 使用 SimpleDateFormat 生成日期部分，格式为"yyyy-MM/dd"
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM/dd");
  // 获取当前日期的格式化字符串
  String datePart = dateFormat.format(new Date());
  // 组合日期部分和文件名，得到完整的对象名称（路径）
  String objectName = datePart + "/" + fileName;
  // ============通用=================

  try {
    // 构建上传参数对象
    PutObjectArgs objectArgs = PutObjectArgs.builder()
        .bucket(prop.getBucketName())
        .object(objectName)
        .stream(file.getInputStream(), file.getSize(), -1)
        .contentType(file.getContentType())
        .build();
    // 使用 MinIO 客户端上传文件到指定对象路径
    minioClient.putObject(objectArgs);
  } catch (Exception e) {
    // 如果上传过程中发生异常，打印异常信息
    e.printStackTrace();
    // 返回 null 表示上传失败
    return null;
  }
  // 返回上传后的对象名称
  return objectName;
}

    /**
     * 预览图片
     *
     * @param fileName
     * @return
     */
  public String preview(String fileName) {
    // 查看文件地址
    GetPresignedObjectUrlArgs build = new GetPresignedObjectUrlArgs().builder().bucket(prop.getBucketName()).object(fileName).method(Method.GET).build();
    try {
      String url = minioClient.getPresignedObjectUrl(build);
      return url;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 文件下载
   *
   * @param fileName 文件名称
   * @param res      response
   * @return Boolean
   */
  public void download(String fileName, HttpServletResponse res) {
    // 构建获取对象的参数对象
    GetObjectArgs objectArgs = GetObjectArgs.builder()
        .bucket(prop.getBucketName())
        .object(fileName)
        .build();
    // 使用 'response' 对象来读取文件对象的数据
    try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
      byte[] buf = new byte[1024];
      int len;
      // 使用缓存输出流，避免一次性将对象数据写入内存
      try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
        // 从 MinIO 获取对象数据并写入临时输出流
        while ((len = response.read(buf)) != -1) {
          os.write(buf, 0, len);
        }
        os.flush();
        //将之前缓存的数据从 FastByteArrayOutputStream 的缓冲区提取出来，并存储在名为 bytes 的字节数组中
        byte[] bytes = os.toByteArray();

        // 设置响应的字符编码为 utf-8
        res.setCharacterEncoding("utf-8");
        // 设置下载的文件名以及强制下载不打开
        res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

        try (ServletOutputStream stream = res.getOutputStream()) {
          // 将对象数据写入响应输出流，实现文件下载
          stream.write(bytes);
          stream.flush();
        }
      }
    } catch (Exception e) {
      // 如果发生异常，打印异常信息
      e.printStackTrace();
    }
  }

  /**
   * 查看文件对象
   *
   * @return 存储bucket内文件对象信息
   */
  public List<Item> listObjects() {
    Iterable<Result<Item>> results = minioClient.listObjects(
        ListObjectsArgs.builder().bucket(prop.getBucketName()).build());
    List<Item> items = new ArrayList<>();
    try {
      for (Result<Item> result : results) {
        items.add(result.get());
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return items;
  }

  /**
   * 删除
   *
   * @param fileName
   * @return
   * @throws Exception
   */
  public boolean remove(String fileName) {
    try {
      minioClient.removeObject(RemoveObjectArgs.builder().bucket(prop.getBucketName()).object(fileName).build());
    } catch (Exception e) {
      return false;
    }
    return true;
  }

}
