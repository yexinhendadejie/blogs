package com.blogs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MsgStatusEnum {

    REPLY_OUT(0, "外层的一级评论回复"),
    REPLY_INSIDE(1, "内层的所有都属于二级回复");

    private Integer id;
    private String type;
}
