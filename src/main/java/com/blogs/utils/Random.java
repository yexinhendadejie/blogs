package com.blogs.utils;

public class Random {
    // 获得指定位数随机数字
    public static String getRandom(Integer digit) {
        Integer value = (int) ((Math.random() * 9 + 1) * Math.pow(10, digit - 1));
        return String.valueOf(value);
    }
}
