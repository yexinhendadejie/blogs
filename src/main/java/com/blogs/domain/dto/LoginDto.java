package com.blogs.domain.dto;

import com.blogs.common.validator.TypeEnum;
import com.blogs.common.validator.anno.LoginTypeEnum;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    // 用户输入邮箱用户名手机号通过密码登录
    private String account;

    @NotBlank(message = "密码不能为空")
    private String pwd;


    @NotBlank(message = "登陆类型不得为空")
    @LoginTypeEnum(enumValue = TypeEnum.LoginType.class)
    private String loginType;
}
