package com.blogs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LikeTypeEnum {
    DOWN(0, "点踩"),
    SUPPORT(1, "点赞");

    private Integer id;
    private String type;
}
