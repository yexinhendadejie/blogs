package com.blogs.domain.dto.user;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDto {

  // 用户Id
  private Integer id;

  // 账号
  private String uname;

  // 密码
  private String pwd;

  // 出生日期
  private Timestamp born;

  // 兴趣爱好
  private String hobby;

  // 个性签名
  private String label;

  // 性别 0->男 1->女
  private Boolean sex;

  // 手机号
  private String phone;

  // 邮箱
  private String email;

  // 修改时间
  private Timestamp updateTime;

  // 创建时间
  private Timestamp createTime;
}
