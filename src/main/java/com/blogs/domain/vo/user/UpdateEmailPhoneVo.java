package com.blogs.domain.vo.user;

import com.blogs.common.validator.TypeEnum;
import com.blogs.common.validator.anno.LoginTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmailPhoneVo {

  private Integer id;

  // 用户名 手机号码 邮箱
  private String account;

  private String loginType;

  public UpdateEmailPhoneVo(String account, String loginType) {
  }
}
