package com.blogs.common.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;


public @interface TypeEnum {

    @AllArgsConstructor
    @Getter
    enum LoginType {
        EMAIL("email", "电子邮箱" ),
        PHONE("phone", "手机号"),
        NAME("uname", "用户名");

        // 名称
        private String name;
        // 描述
        private String desc;
        // 正则验证
    }


}
