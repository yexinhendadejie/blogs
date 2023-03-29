package com.blogs.domain.dto;

import com.blogs.common.config.RegConfig;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
@Data
public class RegisterForPhoneDto {
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegConfig.MOBILE_PHONE, message = "手机号格式不正确(仅支持+86)")
    private String phone;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = RegConfig.PASSWORD, message = "密码只能字母或数字开头且只包括大小写26字母以及下划线(_)的8-16位字符")
    private String pwd;
}
