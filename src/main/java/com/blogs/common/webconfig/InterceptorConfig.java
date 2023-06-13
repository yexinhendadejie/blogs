package com.blogs.common.webconfig;

import com.blogs.utils.UploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String path = UploadUtil.createPublicPath().getPath();

    registry.addResourceHandler("/public/**")
        .addResourceLocations("file:///" + path + File.separator);
  }
}
