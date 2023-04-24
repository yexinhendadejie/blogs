package com.blogs.utils;

import cn.dev33.satoken.stp.StpUtil;

import java.util.Objects;

public class BUtils {

    String key = "asdgiuhuijdahsg923";

    // 获得指定位数随机数字
    public static String getRandom(Integer digit) {
        Integer value = (int) ((Math.random() * 9 + 1) * Math.pow(10, digit - 1));
        return String.valueOf(value);
    }

    // 用SecureUtil加密
    public static String encrypt(String str) {
        return cn.hutool.crypto.SecureUtil.md5(str);
    }

    // 用SecureUtil解密
    public static String decrypt(String str) {
        return cn.hutool.crypto.SecureUtil.md5(str);
    }

}
