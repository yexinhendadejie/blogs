package com.blogs.mapper;

public interface UserMiddleMapper {

  // 查找对应信息粉丝/关注
  Integer findUserMiddle(Integer userId, Integer flag);
}
