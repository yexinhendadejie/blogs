package com.blogs.service;

import com.blogs.domain.dto.user.*;
import com.blogs.domain.vo.user.LoginVo;
import com.blogs.domain.vo.user.UpdateEmailPhoneVo;
import com.blogs.domain.vo.user.UploadVo;
import com.blogs.domain.vo.user.UserVo;
import org.springframework.web.multipart.MultipartFile;

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

    // 修改密码升级版
    String updatePwdUpgrade(UpdatePwdUpgradeDto updatePwdUpgradeDto);

    // 修改用户信息
    void updateUserInfo(UserDto userDto);

    // 修改邮箱
    UpdateEmailPhoneVo updateEmail(UpdateEmailPhoneDto updateEmailPhoneDto);

    // 修改手机号
    UpdateEmailPhoneVo updatePhone(UpdateEmailPhoneDto updateEmailPhoneDto);

    // 上传头像
    UploadVo uploadAvatar(MultipartFile file);

    // 根据ID查询用户信息
    UserVo selectById(Integer id);

    // 根据账号查询用户信息
    UserVo selectByAccount(String account);


}
