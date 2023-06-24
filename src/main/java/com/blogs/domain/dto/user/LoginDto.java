package com.blogs.domain.dto.user;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    // 用户输入邮箱用户名手机号通过密码登录
    private String account;

    @NotBlank(message = "密码不能为空")
    private String pwd;

}
