package com.blogs.service;

import com.blogs.domain.vo.UploadVo;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    UploadVo uploadFile(MultipartFile file);
}
