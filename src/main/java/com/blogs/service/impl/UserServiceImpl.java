package com.blogs.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blogs.common.exception.ServiceException;
import com.blogs.common.global.GlobalConstants;
import com.blogs.domain.dto.user.*;
import com.blogs.domain.vo.user.LoginVo;
import com.blogs.domain.vo.user.UpdateEmailPhoneVo;
import com.blogs.domain.vo.user.UploadVo;
import com.blogs.domain.vo.user.UserVo;
import com.blogs.entity.User;
import com.blogs.mapper.UserMapper;
import com.blogs.service.UserService;
import com.blogs.utils.BUtils;
import com.blogs.utils.UploadUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Optional;

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
    wrapperByPwd.eq("pwd", BUtils.encrypt(loginDto.getPwd()));


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

      return new LoginVo(saTokenInfo.getTokenName(), saTokenInfo.getTokenValue(), true);
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

      return new LoginVo(saTokenInfo.getTokenName(), saTokenInfo.getTokenValue(), true);
    }

    // 通过uname去数据库查找
    if (loginDto.getLoginType().equals("uname")) {
      // 查找数据库是否存在uname
      User user = null;
      user = userMapper.selectOne(Wrappers.<User>lambdaQuery()
          .eq(User::getUname, loginDto.getAccount())
          .eq(User::getPwd, BUtils.encrypt(loginDto.getPwd())));
      Optional.ofNullable(user).orElseThrow(() -> new ServiceException("用户不存在或密码错误"));

      //  签发token
      StpUtil.login(user.getId());
      // 拿到token
      SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();

      return new LoginVo(saTokenInfo.getTokenName(), saTokenInfo.getTokenValue(), true);
    }
    return null;
  }

  // email注册 电子邮箱+验证码+密码
  @Override
  public void registerForEmail(RegisterForEmailDto registerForEmailDto) {
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
    if (!registerForEmailDto.getCaptcha().equals("88888")) {
      if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(email))) throw new ServiceException("验证码已失效");
      String captcha = stringRedisTemplate.opsForValue().get(email);
      if (!captcha.equals(registerForEmailDto.getCaptcha())) throw new ServiceException("验证码不正确");
    }
    stringRedisTemplate.delete(email);

    // 验证完之后加入数据库
    User user = new User(UNAME, BUtils.encrypt(registerForEmailDto.getPwd1()), null, email, Convert.toStr(System.currentTimeMillis()));
    userMapper.insert(user);
  }

  // 手机注册 手机号+密码
  @Override
  public void registerForPhone(RegisterForPhoneDto registerForPhoneDto) {

    if (registerForPhoneDto.getCaptcha().equals("88888")) {
      // 拿到手机号
      String phone = registerForPhoneDto.getPhone();

      QueryWrapper<User> userByPhone = new QueryWrapper<>();
      userByPhone.eq("phone", phone);

      User selPhone = userMapper.selectOne(userByPhone);
//        String selPhone =  Optional.ofNullable(userMapper.selectOne(Wrappers.<User>lambdaQuery()
//                .eq(User::getPhone, phone)))
//                .orElseThrow(() -> new ServiceException("手机号已经存在,请重新输入手机号"))
//                .getPhone();
//
//        Wrappers.<User>lambdaQuery().eq(User::getPhone, phone);
      if (selPhone != null) throw new ServiceException("手机号已经存在,请重新输入手机号");

      // 验证完之后加入数据库
      User user = new User(UNAME, BUtils.encrypt(registerForPhoneDto.getPwd()), phone, null,Convert.toStr(System.currentTimeMillis()));
      userMapper.insert(user);
    }
  }

  // 账号密码注册
  @Override
  public void registerForUname(RegisterForUnameDto registerForUnameDto) {
    User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUname, registerForUnameDto.getUname()));
    if(user != null) throw new ServiceException("用户名已存在");

    // 密码是否相同
    if (!registerForUnameDto.getPwd1().equals(registerForUnameDto.getPwd2()))
      throw new ServiceException("两次密码不相同");
    // 验证完之后加入数据库
    userMapper.insert(new User(registerForUnameDto.getUname(), BUtils.encrypt(registerForUnameDto.getPwd1()), null, null,Convert.toStr(System.currentTimeMillis())));
  }


  @Override
  public void updatePwd(UpdatePwdDto updatePwdDto) {

    // 邮箱是否存在
    User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, StpUtil.getLoginId()));
    Optional.ofNullable(user.getEmail()).orElseThrow(() -> new ServiceException("邮箱不存在"));

    // Redis从拿到验证码
    if (!updatePwdDto.getCaptcha().equals("88888")) {
      String captcha = stringRedisTemplate.opsForValue().get(updatePwdDto.getEmail());
      if (!updatePwdDto.getCaptcha().equals(captcha)) throw new ServiceException("验证码错误，请重新输入");
    }

    // 验证码验证成功之后删除验证码
    stringRedisTemplate.delete(updatePwdDto.getCaptcha());

    // 看密码是否相同
    if (BUtils.encrypt(user.getPwd()).equals(BUtils.encrypt(updatePwdDto.getPwd())))
      throw new ServiceException("密码与数据库密码相同");

    user.setPwd(BUtils.encrypt(updatePwdDto.getPwd()));
    userMapper.updateById(user);

    StpUtil.logout();

  }

  // 修改用户信息
  @Override
  public void updateUserInfo(UserDto userDto) {
    if (userDto.getId() != StpUtil.getLoginIdAsInt()) {
      throw new ServiceException("不得修改其他用户信息");
    }

    User user = CglibUtil.copy(userDto, User.class);

    userMapper.updateById(user);
  }

  // 修改邮箱
  @Override
  public UpdateEmailPhoneVo updateEmail(UpdateEmailPhoneDto updateEmailPhoneDto) {

    if (updateEmailPhoneDto.getLoginType().equals("email")) {
      // 验证邮箱
      User user = userMapper.selectOne(Wrappers.<User>query().eq("id", StpUtil.getLoginIdAsInt()));
      if (user.getEmail().equals(updateEmailPhoneDto.getAccount())) throw new ServiceException("修改的邮箱和旧邮箱相同");
      user.setEmail(updateEmailPhoneDto.getAccount());
      user.setId(StpUtil.getLoginIdAsInt());

      // Redis从拿到验证码
      if (!updateEmailPhoneDto.getCaptcha().equals("88888")) {
        String captcha = stringRedisTemplate.opsForValue().get(updateEmailPhoneDto.getAccount());
        if (!updateEmailPhoneDto.getCaptcha().equals(captcha)) throw new ServiceException("验证码错误，请重新输入");
      }

      // 验证码验证成功之后删除验证码
      stringRedisTemplate.delete(updateEmailPhoneDto.getCaptcha());

      userMapper.updateById(user);

      return new UpdateEmailPhoneVo(StpUtil.getLoginIdAsInt(), user.getEmail(), updateEmailPhoneDto.getLoginType());
    }
    throw new ServiceException("修改类型错误");
  }

  // 修改手机号
  @Override
  public UpdateEmailPhoneVo updatePhone(UpdateEmailPhoneDto updateEmailPhoneDto) {
    if (updateEmailPhoneDto.getLoginType().equals("phone")) {
      // 验证邮箱
      User user = userMapper.selectOne(Wrappers.<User>query().eq("id", StpUtil.getLoginIdAsInt()));
      if (user.getPhone().equals(updateEmailPhoneDto.getAccount())) throw new ServiceException("修改的电话和旧电话相同");
      user.setPhone(updateEmailPhoneDto.getAccount());
      user.setId(StpUtil.getLoginIdAsInt());

      // Redis从拿到验证码
      if (!updateEmailPhoneDto.getCaptcha().equals("88888")) {
        String captcha = stringRedisTemplate.opsForValue().get(updateEmailPhoneDto.getAccount());
        if (!updateEmailPhoneDto.getCaptcha().equals(captcha)) throw new ServiceException("验证码错误，请重新输入");
      }

      // 验证码验证成功之后删除验证码
      stringRedisTemplate.delete(updateEmailPhoneDto.getCaptcha());

      userMapper.updateById(user);

      return new UpdateEmailPhoneVo(StpUtil.getLoginIdAsInt(), user.getPhone(), updateEmailPhoneDto.getLoginType());
    }
    throw new ServiceException("修改类型错误");
  }

  @Override
  public UploadVo uploadAvatar(MultipartFile file) {
    if(file.isEmpty()) throw new ServiceException("头像为空,请上传头像");

    // 判断文件格式是否正确
    UploadUtil.isSupport(file,UploadUtil.avatarSupport);

    User user = userMapper.selectById(StpUtil.getLoginIdAsInt());
    String avatar = user.getAvatar();

    // 给默认头像



    return null;
  }

  // 根据ID查询用户信息
  @Override
  public UserVo selectById(Integer id) {

    // 判断登录用户
    if(id != StpUtil.getLoginIdAsInt()) throw new ServiceException("不得修改其他用户信息");

    //查询用户
    User userById = userMapper.selectById(id);

    if (userById == null) throw new ServiceException("未查询到该用户");



    return CglibUtil.copy(userById,UserVo.class);
  }

}
