package com.blogs.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMiddleMapper {

  // 查找对应信息粉丝/关注
  Integer findUserMiddle(Integer userId, Integer flag);

}
