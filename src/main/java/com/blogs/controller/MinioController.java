package com.blogs.controller;

import com.blogs.common.config.MinioConfig;
import com.blogs.utils.MinioUtil;
import com.blogs.utils.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/minio")
public class MinioController {


  @Resource
  private MinioUtil minioUtil;
  @Resource
  private MinioConfig prop;

  @GetMapping("/bucketExists/{bucketName}")
  public Resp bucketExists(@PathVariable String bucketName) {
    return Resp.ok().put("bucketName",minioUtil.bucketExists(bucketName));
  }

  @GetMapping("/makeBucket/{bucketName}")
  public Resp makeBucket(@PathVariable String bucketName) {
    return Resp.ok().put("bucketName",minioUtil.makeBucket(bucketName));
  }

  @GetMapping("/removeBucket")
  public Resp removeBucket(String bucketName) {
    return Resp.ok().put("bucketName",minioUtil.removeBucket(bucketName));
  }

  @GetMapping("/getAllBuckets")
  public Resp getAllBuckets() {
    List<String> allBuckets = minioUtil.getAllBuckets();
    return Resp.ok().put("allBuckets",allBuckets);
  }

  // 上传文件
  @PostMapping("/upload")
  public Resp upload(@RequestParam("file") MultipartFile file) {
    String objectName = minioUtil.upload(file);
    if (null != objectName) {
      return Resp.ok().put("url",(prop.getEndpoint() + "/" + prop.getBucketName() + "/" + objectName));
    }
    return Resp.error();
  }

  // 上传头像
  @PostMapping("/uploadAvatar")
  public Resp uploadAvatar(@RequestParam("file") MultipartFile file) {
    String objectName = minioUtil.uploadAvatar(file);
    if (null != objectName) {
      return Resp.ok().put("url",(prop.getEndpoint() + "/" + prop.getBucketName() + "/" + objectName));
    }
    return Resp.error();
  }

  @GetMapping("/preview")
  public Resp preview(@RequestParam("fileName") String fileName) {
    return Resp.ok().put("filleName",minioUtil.preview(fileName));
  }

  @GetMapping("/download")
  public Resp download(@RequestParam("fileName") String fileName, HttpServletResponse res) {
    minioUtil.download(fileName,res);
    return Resp.ok();
  }

  @PostMapping("/delete")
  public Resp remove(String url) {
    String objName = url.substring(url.lastIndexOf(prop.getBucketName()+"/") + prop.getBucketName().length()+1);
    minioUtil.remove(objName);
    return Resp.ok().put("objName",objName);
  }

}
