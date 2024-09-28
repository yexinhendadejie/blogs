package com.blogs.common.anno;

import com.blogs.common.enums.ImEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImStrategy {
    ImEnum.Strategy value();
}
