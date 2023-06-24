package com.blogs.utils.email;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.blogs.common.global.GlobalConstants;
import com.blogs.utils.BUtils;
import com.blogs.utils.BUtils;

import java.util.UUID;

public class MailerUpgrade {
  private Integer expirationTime = GlobalConstants.CAPTCHA_EXPIRED;
  private String title = GlobalConstants.PROJECTNAME;

  public MailerUpgrade() {
  }

  public MailerUpgrade(Integer expirationTime) {
    this.expirationTime = expirationTime;
  }

  public MailerUpgrade(String title) {
    this.title = title;
  }

  public MailerUpgrade(String title, Integer expirationTime) {
    this.expirationTime = expirationTime;
    this.title = title;
  }

  public String sendTokenUrl(String to) {
    MailAccount account = new MailAccount();
    account.setSslProtocols("TLSv1.2");
    account.setPort(465);
    account.setFrom("Hurd Jay<1471172689@qq.com>");
    account.setUser("1471172689");
    account.setPass("aresmlaisjodhacc");
    account.setStarttlsEnable(true);
    account.setSslEnable(true);
    account.setSocketFactoryClass("javax.net.ssl.SSLSocketFactory");

    String tokenUrl = "http://localhost:3314/#/forgetPasswordUpgradeSetPwd?token=" + UUID.randomUUID();
    MailUtil.send(account, to, title, "<div style=\"background:#f8f8f8\">\n" +
        "  <h2 style=\"text-align:center;color:#fff;background: #6762C5;padding: 20px 0;\">邮箱验证码</h2>\n" +
        "    <div style=\"padding: 20px 30px 50px;\">\n" +
        "      <p style=\"font-size:20px\">你好, 用户</p>\n" +
        "      <p style=\"margin-top: 30px;\">请尽快填写以下验证码完成修改密码验证(" + expirationTime + "分钟内有效)</p>\n" +
        "      <h1 style=\"text-align: center;font-weight: 900;padding:40px;text-decoration: none;font-size:12px\">" + tokenUrl + "</h1>\n" +
        "      <p style=\"font-size: 12px;color: #888\">本邮件由服务器发送, 用于邮箱验证, 请勿回复此邮件。谢谢</p>\n" +
        "    </div>\n" +
        "</div>", true);
    return tokenUrl;
  }

}
