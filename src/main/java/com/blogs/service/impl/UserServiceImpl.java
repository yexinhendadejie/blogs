package com.blogs.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blogs.common.exception.ServiceException;
import com.blogs.domain.dto.LoginDto;
import com.blogs.domain.dto.RegisterForEmailDto;
import com.blogs.domain.dto.RegisterForPhoneDto;
import com.blogs.domain.vo.LoginVo;
import com.blogs.entity.User;
import com.blogs.mapper.UserMapper;
import com.blogs.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.blogs.common.global.GlobalConstants.UNAME;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 登陆
    @Override
    public LoginVo login(LoginDto loginDto) {
        QueryWrapper<User> wrapperByPwd = new QueryWrapper<>();
        wrapperByPwd.eq("pwd", loginDto.getPwd());


        // 通过手机号去数据库查找
        if (loginDto.getLoginType().equals("phone")) {
            // 创建条件
            QueryWrapper<User> wrapperByPhone = new QueryWrapper<>();
            wrapperByPhone.eq("phone", loginDto.getAccount());
            User userForPhone = userMapper.selectOne(wrapperByPhone);
            if (userForPhone == null) throw new ServiceException("电话号码不存在");

            wrapperByPwd.eq("phone", loginDto.getAccount());
            User userForPwd = userMapper.selectOne(wrapperByPwd);
            if (userForPwd == null) throw new ServiceException("密码错误");

            //  签发token
            StpUtil.login(userForPwd.getId());
            // 拿到token
            SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();

            LoginVo loginVo = new LoginVo(saTokenInfo.getTokenName(), saTokenInfo.getTokenValue(), true);
            return loginVo;
        }

        // 通过邮箱去数据库查找
        if (loginDto.getLoginType().equals("email")) {
            // 创建条件
            QueryWrapper<User> wrapperByEmail = new QueryWrapper<>();
            wrapperByEmail.eq("email", loginDto.getAccount());
            User userForPhone = userMapper.selectOne(wrapperByEmail);
            if (userForPhone == null) throw new ServiceException("邮箱不存在");

            wrapperByPwd.eq("email", loginDto.getAccount());
            User userForPwd = userMapper.selectOne(wrapperByPwd);
            if (userForPwd == null) throw new ServiceException("密码错误");

            //  签发token
            StpUtil.login(userForPwd.getId());
            // 拿到token
            SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();

            LoginVo loginVo = new LoginVo(saTokenInfo.getTokenName(), saTokenInfo.getTokenValue(), true);
            return loginVo;
        }

        // 通过uname去数据库查找
        if (loginDto.getLoginType().equals("uname")) {
            // 创建条件
            QueryWrapper<User> wrapperByUname = new QueryWrapper<>();
            wrapperByUname.eq("uname", loginDto.getAccount());
            User userForPhone = userMapper.selectOne(wrapperByUname);
            if (userForPhone == null) throw new ServiceException("uname用户不存在");

            wrapperByPwd.eq("uname", loginDto.getAccount());
            User userForPwd = userMapper.selectOne(wrapperByPwd);
            if (userForPwd == null) throw new ServiceException("密码错误");

            //  签发token
            StpUtil.login(userForPwd.getId());
            // 拿到token
            SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();

            LoginVo loginVo = new LoginVo(saTokenInfo.getTokenName(), saTokenInfo.getTokenValue(), true);
            return loginVo;
        }
        return null;
    }

    // email注册 电子邮箱+验证码+密码
    @Override
    public Void registerForEmail(RegisterForEmailDto registerForEmailDto) {
        // 拿到邮件
        String email = registerForEmailDto.getEmail();

        // 验证邮箱
        QueryWrapper<User> userByEmail = new QueryWrapper<>();
        userByEmail.eq("email", registerForEmailDto.getEmail());
        User userEmail = userMapper.selectOne(userByEmail);
        if (userEmail != null) throw new ServiceException("邮箱已存在");
        if (!registerForEmailDto.getPwd1().equals(registerForEmailDto.getPwd2()))
            throw new ServiceException("两次密码不相同");


        // 验证码验证
        if (!stringRedisTemplate.hasKey(email)) throw new ServiceException("验证码已失效");
        String captcha = stringRedisTemplate.opsForValue().get(email);
        if (!captcha.equals(registerForEmailDto.getCaptcha())) throw new ServiceException("验证码不正确");
        stringRedisTemplate.delete(email);


        // 验证完之后加入数据库
        User user = new User(UNAME,registerForEmailDto.getPwd1(),null,email);
        userMapper.insert(user);
        return null;
    }

    // 手机注册 手机号+密码
    @Override
    public Void registerForPhone(RegisterForPhoneDto registerForPhoneDto) {
        // 拿到手机号
        String phone = registerForPhoneDto.getPhone();

        QueryWrapper<User> userByPhone = new QueryWrapper<>();
        userByPhone.eq("phone",phone);

        User selPhone = userMapper.selectOne(userByPhone);
        if(selPhone != null) throw new ServiceException("手机号已经存在,请重新输入手机号");

        // 验证完之后加入数据库
        User user = new User(UNAME, registerForPhoneDto.getPwd(), phone,null);
        userMapper.insert(user);
        return null;
    }


}
