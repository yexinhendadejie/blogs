package com.blogs.controller;

import com.blogs.domain.vo.user.UploadVo;
import com.blogs.service.UploadService;
import com.blogs.utils.Resp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/upload")
public class UploadController {


  @Resource
  private UploadService uploadService;

  @PostMapping("/file")
  public Resp<UploadVo> upload(@RequestParam("file") MultipartFile file) {
    UploadVo uploadVo = uploadService.uploadFile(file);
    return Resp.ok(uploadVo).msg("文件上传成功");
  }
}
