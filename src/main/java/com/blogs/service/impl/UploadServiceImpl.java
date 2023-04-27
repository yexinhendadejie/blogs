package com.blogs.service.impl;

import com.blogs.common.exception.ServiceException;
import com.blogs.common.resultcode.ResultCodeEnum;
import com.blogs.domain.vo.user.UploadVo;
import com.blogs.service.UploadService;
import com.blogs.utils.UploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public UploadVo uploadFile(MultipartFile file) {
        if (file.isEmpty()) throw new ServiceException(ResultCodeEnum.FILE_UPLOAD_ERROR, "请上传文件");
        UploadVo uploadVo = new UploadVo();

        // 上传文件
        UploadUtil.upload(file);
        // 获得是图片还是文件
        uploadVo.setType(UploadUtil.getTypePath(UploadUtil.getSuffix(file.getOriginalFilename())));
        // 获得文件名
        uploadVo.setFileName(file.getOriginalFilename());
        return uploadVo;
    }
}
