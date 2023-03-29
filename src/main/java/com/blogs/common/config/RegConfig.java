package com.blogs.common.config;


/**
 * 正则
 */
public class RegConfig {
  public static final String MOBILE_PHONE = "^1\\d{10}$";
  public static final String PASSWORD = "^[A-Za-z\\d]\\w{7,15}$";
  public static final String EMAIL = "^\\w+@\\w+\\.\\w+{2,}$";

  // 非0正整数
  public static final String NON_ZERO_POSITIVE_NUM = "^((0\\.){0,1}[1-9]+\\d*(\\.\\d{1,2})?)|(0+\\.0[1-9])|(0+\\.[1-9]\\d?)$";


}