package com.blogs.service;

import com.blogs.domain.dto.LoginDto;
import com.blogs.domain.dto.RegisterForEmailDto;
import com.blogs.domain.dto.RegisterForPhoneDto;
import com.blogs.domain.vo.LoginVo;
import com.blogs.entity.User;
import io.swagger.models.auth.In;

public interface UserService {

    LoginVo login(LoginDto loginDto);

    void registerForEmail(RegisterForEmailDto registerForEmailDto);

    void registerForPhone(RegisterForPhoneDto registerForPhoneDto);


}
