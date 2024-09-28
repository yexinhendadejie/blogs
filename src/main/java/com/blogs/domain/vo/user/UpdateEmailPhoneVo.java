package com.blogs.domain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmailPhoneVo {
  private Integer id;

  // 用户名 手机号码 邮箱
  private String account;

  private String loginType;

  public UpdateEmailPhoneVo(String email, String email1) {
  }
}
