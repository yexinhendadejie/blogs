package com.blogs.common.validator.anno;

import com.blogs.common.validator.SensitiveJsonSerializerValidator;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.yomahub.tlog.example.feign.enumnew.SensitiveStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口返回数据如何优雅的实现脱敏？
 * @className: Sensitive
 * @author: woniu
 * @date: 2023/3/8
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveJsonSerializerValidator.class)
public @interface Sensitive {
  //脱敏策略
  SensitiveStrategy strategy();
}
