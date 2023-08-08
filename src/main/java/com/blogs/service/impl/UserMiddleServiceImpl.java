package com.blogs.service.impl;

import com.blogs.domain.dto.media.UserMediaDto;
import com.blogs.mapper.UserMiddleMapper;
import com.blogs.service.UserMiddleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class UserMiddleServiceImpl implements UserMiddleService {

  @Resource
  private UserMiddleMapper userMiddleMapper;

  @Override
  public Integer sumUserMiddle(UserMediaDto userMediaDto) {
    return userMiddleMapper.findUserMiddle(userMediaDto.getUserId(), userMediaDto.getFlag());
  }
}
