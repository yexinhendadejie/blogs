package com.blogs.common.webconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 解决跨域问题
@Configuration
public class CorsConfigure implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        // 允许cookie
        .allowCredentials(true)
        // 放行原始源
        .allowedOriginPatterns("*")
        // 放行请求方法
        .allowedMethods("*")
        // 放行请求头
        .allowedHeaders("*").exposedHeaders("*");
  }
}
