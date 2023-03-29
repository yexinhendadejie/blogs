package com.blogs.common.config;

import cn.dev33.satoken.stp.StpInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 *
 * sa-token 自定义权限验证接口扩展
 *
 * */
@Slf4j

@Component
public class StpInterfaceImpl implements StpInterface {

  @Override
  public List<String> getPermissionList(Object loginId, String s) {
    return null;
  }

  @Override
  public List<String> getRoleList(Object loginId, String s) {

    return null;
  }
}
