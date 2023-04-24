package com.blogs.domain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
  private String tokenName;
  private String token;
  private Boolean hasPwd;
}
