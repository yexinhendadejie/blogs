package com.blogs.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.blogs.domain.dto.user.*;
import com.blogs.domain.vo.user.LoginVo;
import com.blogs.domain.vo.user.UpdateEmailPhoneVo;
import com.blogs.domain.vo.user.UserVo;
import com.blogs.service.UserService;
import com.blogs.utils.Resp;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

  @Resource
  private UserService userService;

  // 登陆
  @PostMapping("/login")
  public Resp<LoginVo> login(@Validated @RequestBody LoginDto loginDto) {
    LoginVo loginVo = userService.login(loginDto);
    return Resp.ok(loginVo).msg("登陆成功");
  }

  // 邮件注册
  @PostMapping("/registerForEmail")
  public Resp<Void> registerForEmail(@Validated @RequestBody RegisterForEmailDto registerForEmailDto) {
    userService.registerForEmail(registerForEmailDto);
    return Resp.ok().msg("添加成功");
  }

  // 手机号注册 验证码只能是8888
  @PostMapping("/registerForPhone")
  public Resp<Void> registerForPhone(@Validated @RequestBody RegisterForPhoneDto registerForPhoneDto) {
    userService.registerForPhone(registerForPhoneDto);
    return Resp.ok().msg("添加成功");
  }

  // 账号注册
  @PostMapping("/registerForUname")
  public Resp<Void> registerForUname(@Validated @RequestBody RegisterForUnameDto registerForUnameDto) {
    userService.registerForUname(registerForUnameDto);
    return Resp.ok().msg("添加成功");
  }

  // 修改密码
  @PostMapping("/updatePwd")
  public Resp<Void> updatePwd(@Validated @RequestBody UpdatePwdDto updatePwdDto) {
    userService.updatePwd(updatePwdDto);
    return Resp.ok().msg("修改成功");
  }

  // 登出
  @GetMapping("/logout")
  public Resp<Void> logout() {
    StpUtil.logout();
    return Resp.ok().msg("退出成功");
  }

  // 修改用户信息
  @PostMapping("/updateUserInfo")
  public Resp<Void> updateUserInfo(@Validated @RequestBody UserDto userDto) {
    userService.updateUserInfo(userDto);
    return Resp.ok().msg("修改成功");
  }

  // 修改邮箱
  @PostMapping("/updateEmail")
  public Resp<UpdateEmailPhoneVo> updateEmail(@Validated @RequestBody UpdateEmailPhoneDto updateEmailPhoneDto) {
    return Resp.ok(userService.updateEmail(updateEmailPhoneDto));
  }

  // 修改手机号
  @PostMapping("/updatePhone")
  public Resp<UpdateEmailPhoneVo> updatePhone(@Validated @RequestBody UpdateEmailPhoneDto updateEmailPhoneDto) {
    return Resp.ok(userService.updatePhone(updateEmailPhoneDto));
  }

  // 根据ID查询用户信息
  @GetMapping ("/{id}")
  public Resp<UserVo> selectById(@PathVariable Integer id) {
    return Resp.ok(userService.selectById(id));
  }
}