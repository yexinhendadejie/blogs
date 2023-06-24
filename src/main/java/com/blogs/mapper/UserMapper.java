package com.blogs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blogs.domain.dto.user.LoginDto;
import com.blogs.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Mapper
public interface UserMapper extends BaseMapper<User> {
  User getLoginUser(LoginDto loginDto);

  // 根据账号查询用户
  User findByAccount(@Param("account") String account);
}
