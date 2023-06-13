package com.blogs.domain.vo.user;

import com.blogs.common.validator.anno.Sensitive;
import com.yomahub.tlog.example.feign.enumnew.SensitiveStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
  // 用户Id
  private Integer id;

  // uuid
  private String uuid;

  // 账号
  private String uname;

  // 密码
  @Sensitive(strategy = SensitiveStrategy.PASSWORD)
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
