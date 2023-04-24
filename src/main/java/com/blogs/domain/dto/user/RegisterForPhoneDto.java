package com.blogs.domain.dto.user;

import com.blogs.common.config.RegConfig;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterForPhoneDto {
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegConfig.MOBILE_PHONE, message = "手机号格式不正确(仅支持+86)")
    private String phone;

    @NotBlank(message = "验证码不得为空")
    @Size(min = 5, max = 5, message = "验证码位数不正确")
    private String captcha;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = RegConfig.PASSWORD, message = "密码只能字母或数字开头且只包括大小写26字母以及下划线(_)的8-16位字符")
    private String pwd;
}
