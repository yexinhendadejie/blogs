package com.blogs.domain.dto.user;

import com.blogs.common.validator.TypeEnum;
import com.blogs.common.validator.anno.LoginTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UpdateEmailPhoneDto {

  private Integer id;

  // 用户名 手机号码 邮箱 后期省略了 手机号码和 用户名
  private String email;

  @NotBlank(message = "验证码不得为空")
  @Size(min = 5, max = 5, message = "验证码位数不正确")
  private String captcha;

  @NotBlank(message = "登陆类型不得为空")
  @LoginTypeEnum(enumValue = TypeEnum.LoginType.class)
  private String loginType;

}
