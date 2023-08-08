package com.blogs.domain.dto.user;

import com.blogs.common.config.RegConfig;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
public class UserDto {

  // 用户Id
  private Integer id;

  // 账号
  @NotBlank(message = "昵称不能为空")
  @Size(max = 9, message = "昵称不得超过9位数")
  private String uname;

  // 出生日期不能为空
  @NotNull(message = "出生日期不能为空")
  private Timestamp born;

  // 兴趣爱好
  @Size(max = 25, message = "兴趣爱好不得超过25位数")
  private String hobby;

  // 个性签名
  @Size(max = 25, message = "个性签名不得超过25位数")
  private String label;

  // 性别 0->男 1->女
  private Boolean sex;

  // 修改时间
  private Timestamp updateTime;

  // 创建时间
  private Timestamp createTime;
}
