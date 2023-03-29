package com.blogs.controller;

import com.blogs.common.config.RegConfig;
import com.blogs.common.global.GlobalConstants;
import com.blogs.common.resultcode.ResultCodeEnum;
import com.blogs.utils.Resp;
import com.blogs.utils.email.Mailer;
import io.swagger.annotations.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Api(tags = "邮箱操作相关接口")
@RestController
@RequestMapping("/send")
public class SendController {
  @Resource
  private StringRedisTemplate stringRedisTemplate;


  @ApiOperation("邮箱验证获取验证码")
  @GetMapping("/sendMailCaptcha/{email}")
  public Resp sendMailCaptcha(@PathVariable @ApiParam(name ="email",value = "邮箱") String email){
    // 判断邮箱格式是否正确
    if(!Pattern.matches(RegConfig.EMAIL,email)) return Resp.error(ResultCodeEnum.DATA_FORMAT_ERROR);
    // 发送验证码
    Mailer mailer = new Mailer();
    String captcha = mailer.sendCaptcha(email);
    // 往redis中加入键值，并设置过期时间
    stringRedisTemplate.opsForValue().set(email, captcha, GlobalConstants.CAPTCHA_EXPIRED, TimeUnit.MINUTES);
    return Resp.ok(ResultCodeEnum.SEND_OK);
  }
}
