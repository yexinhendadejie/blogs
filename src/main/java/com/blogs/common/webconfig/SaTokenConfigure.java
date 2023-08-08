package com.blogs.common.webconfig;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;

import com.blogs.common.global.GlobalConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 *
 *sa-token配置类
 *
 * */

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.setAllowCredentials(false);
    config.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SaInterceptor(handle -> {
      SaRouter.match("/**")
          .notMatch(GlobalConstants.EXCLUDE_PATH_PATTERNS)
          .notMatch("/send/**")
          .notMatch("/user/updatePwd")
          .notMatch("/user/login")
          .notMatch("/user/registerForEmail")
          .notMatch("/user/updatePwdUpgrade")
          .notMatch("/user/registerForPhone")
          .notMatch("/user/registerForUname")
          .check(r -> StpUtil.checkLogin());
    })).addPathPatterns("/**");
  }
}
