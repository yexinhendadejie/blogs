package com.blogs.common.global;

import cn.hutool.core.convert.Convert;

public class GlobalConstants {

    public static final String PROJECTNAME = "Blogs";
    public static final Integer CAPTCHA_DIGIT = 5;
    // 单位min
    public static final Integer CAPTCHA_EXPIRED = 5;

    public static final Integer PAGE_SIZE_DEFAULT = 3;
    public static final String UNAME = "user"+ Convert.toStr(System.currentTimeMillis());

    public static final int DEFAULT_PART_SIZE = 5 * 1024 * 1024; // 5MB

    public static final Integer RECRUITMENT_LIST_PAGE_SIZE = 10;
    public static final Integer HUNT_LIST_PAGE_SIZE = 10;
    public static final Double AVATAR_QUALITY = 0.2;
    public static final Double AVATAR_SCALE = 0.3;

    public static final Double AVATAR_COMPRESS_POINT = 100.00 * 1024;
    public static final Integer DEFAULT_ROLE = 2;

    public static final String TOKEN_NAME = "authorization";


    //放行路径
    public static final String[] EXCLUDE_PATH_PATTERNS = {
            // Swagger
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/**",
            "/error"
    };

}
