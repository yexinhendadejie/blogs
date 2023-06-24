package com.blogs.controller;

import com.blogs.common.config.RegConfig;
import com.blogs.common.global.GlobalConstants;
import com.blogs.common.resultcode.ResultCodeEnum;
import com.blogs.utils.Resp;
import com.blogs.utils.email.Mailer;
import com.blogs.utils.email.MailerUpgrade;
import io.swagger.annotations.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/send")
public class SendController {
  @Resource
  private StringRedisTemplate stringRedisTemplate;

  @GetMapping("/sendMailCaptcha/{email}")
  public Resp sendMailCaptcha(@PathVariable String email){
    // 判断邮箱格式是否正确
    if(!Pattern.matches(RegConfig.EMAIL,email)) return Resp.error(ResultCodeEnum.DATA_FORMAT_ERROR);
    // 发送验证码
    Mailer mailer = new Mailer();
    String captcha = mailer.sendCaptcha(email);
    // 往redis中加入键值，并设置过期时间
    stringRedisTemplate.opsForValue().set(email, captcha, GlobalConstants.CAPTCHA_EXPIRED, TimeUnit.MINUTES);
    return Resp.ok(ResultCodeEnum.SEND_OK);
  }

  @GetMapping("/sendMailToken/{email}")
  public Resp sendMailToken(@PathVariable String email){
    // 判断邮箱格式是否正确
    if(!Pattern.matches(RegConfig.EMAIL,email)) return Resp.error(ResultCodeEnum.DATA_FORMAT_ERROR);
    // 发送网址
    MailerUpgrade mailer = new MailerUpgrade();
    String tokenUrl = mailer.sendTokenUrl(email);
    // 往redis中加入键值，并设置过期时间
    stringRedisTemplate.opsForValue().set(email, tokenUrl, GlobalConstants.CAPTCHA_EXPIRED, TimeUnit.MINUTES);
    return Resp.ok(ResultCodeEnum.SEND_OK);
  }
}
