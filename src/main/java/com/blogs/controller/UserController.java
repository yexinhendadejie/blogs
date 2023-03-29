package com.blogs.controller;

import com.blogs.domain.dto.LoginDto;
import com.blogs.domain.dto.RegisterForEmailDto;
import com.blogs.domain.dto.RegisterForPhoneDto;
import com.blogs.domain.vo.LoginVo;
import com.blogs.service.UserService;
import com.blogs.utils.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/registerForPhone")
    public Resp<Void> registerForPhone(@Validated @RequestBody RegisterForPhoneDto registerForPhoneDto) {
        userService.registerForPhone(registerForPhoneDto);
        return Resp.ok().msg("添加成功");
    }

}