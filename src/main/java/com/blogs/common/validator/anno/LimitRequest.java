package com.blogs.common.validator.anno;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitRequest {
    //毫秒，分钟，小时 之间的转换用算数
    long oneMinuteInMillis() default 60000; // 1分钟的毫秒数

    int count() default Integer.MAX_VALUE; // 允许请求的次数

}