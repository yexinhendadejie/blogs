package com.blogs.service;

import com.blogs.domain.dto.media.UserMediaDto;

public interface UserMiddleService {

    // 查找对应信息粉丝/关注
    Integer sumUserMiddle(UserMediaDto userMediaDto);
}
