package com.blogs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @TableId(value = "id", type = IdType.AUTO)
  // 用户Id
  private Integer id;

  // uuid
  private String uuid;

  // 账号
  private String uname;

  // 密码
  private String pwd;

  // 头像
  private String avatar;

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


  public User(String username, String pwd, String phone, String email, String uuid) {
    this.uname = username;
    this.pwd = pwd;
    this.phone = phone;
    this.email = email;
    this.uuid = uuid;
  }
}
