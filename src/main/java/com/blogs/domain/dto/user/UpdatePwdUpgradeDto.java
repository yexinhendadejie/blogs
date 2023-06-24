package com.blogs.domain.dto.user;

import com.blogs.common.config.RegConfig;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdatePwdUpgradeDto {


  @NotBlank(message = "邮箱不得为空")
  @Pattern(regexp = RegConfig.EMAIL, message = "邮箱格式不正确")
  private String email;

  @NotBlank(message = "token不得为空")
  private String token;

  @NotBlank(message = "密码不得为空")
  @Pattern(regexp = RegConfig.PASSWORD, message = "密码只能字母或数字开头且只包括大小写26字母以及下划线(_)的8-16位字符")
  private String pwd1;

  @NotBlank(message = "密码不得为空")
  @Pattern(regexp = RegConfig.PASSWORD, message = "密码只能字母或数字开头且只包括大小写26字母以及下划线(_)的8-16位字符")
  private String pwd2;

}
