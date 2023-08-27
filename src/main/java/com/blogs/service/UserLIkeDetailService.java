package com.blogs.service;

import com.blogs.domain.vo.LikeTypeVo;

public interface UserLIkeDetailService {

    LikeTypeVo likeType(Integer userId, Integer targetId);
}
