package com.blogs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LikeTypeEnum {
    down(0, "点踩"),
    support(1, "点赞");

    private Integer id;
    private String type;
}
