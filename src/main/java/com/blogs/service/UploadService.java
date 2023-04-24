package com.blogs.service;

import com.blogs.domain.vo.user.UploadVo;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    // 上传文件 头像
    UploadVo uploadFile(MultipartFile file);
}
