package com.blogs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MsgTypeEnum {

    COMMENT(2, "评论"),
    REPLY(3, "回复");

    private Integer id;
    private String type;
}
