package com.blogs.domain.dto.user;

import com.blogs.common.validator.anno.ImageFormat;
import lombok.Data;

@Data
public class ImageDto {
    // 用户id
    private Integer id;
    // 用户头像

    @ImageFormat
    private String avatar;
}
