package com.blogs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeEnum {

    BLOG(0, "博客");

    private Integer id;
    private String type;
}
