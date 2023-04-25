package com.blogs.service;

import com.blogs.domain.dto.user.*;
import com.blogs.domain.vo.user.LoginVo;
import com.blogs.domain.vo.user.UpdateEmailPhoneVo;

public interface UserService {

    LoginVo login(LoginDto loginDto);

    // 邮箱注册
    void registerForEmail(RegisterForEmailDto registerForEmailDto);

    // 手机号注册
    void registerForPhone(RegisterForPhoneDto registerForPhoneDto);

    // 账号密码注册
    void registerForUname(RegisterForUnameDto registerForUnameDto);

    // 修改密码
    void updatePwd(UpdatePwdDto updatePwdDto);

    // 修改用户信息
    void updateUserInfo(UserDto userDto);

    // 修改邮箱
    UpdateEmailPhoneVo updateEmail(UpdateEmailPhoneDto updateEmailPhoneDto);

    // 修改手机号

    UpdateEmailPhoneVo updatePhone(UpdateEmailPhoneDto updateEmailPhoneDto);
}
