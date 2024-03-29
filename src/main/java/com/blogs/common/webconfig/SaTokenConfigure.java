package com.blogs.common.webconfig;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.blogs.common.global.GlobalConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
*
*sa-token配置类
*
* */

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SaInterceptor(handle -> {
      SaRouter.match("/**")
              .notMatch(GlobalConstants.EXCLUDE_PATH_PATTERNS)
              .notMatch("/send/**")
              .notMatch("/user/**")
              .check(r -> StpUtil.checkLogin());
    })).addPathPatterns("/**");
  }
}
