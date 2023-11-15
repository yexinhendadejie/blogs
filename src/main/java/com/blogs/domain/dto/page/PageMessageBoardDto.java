package com.blogs.domain.dto.page;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PageMessageBoardDto extends PageDto {

    /**
     * 主键id
     */
    private Integer id;

    // 表示评论人的id
    private Integer fromId;

    // 是评论目标人的id
    private Integer toId;

    // 内容
    private String content;

    // 类型
    private Integer msgType;

    // 当前账号的所有id
    private Integer findAccountBoard;


    // CreateTime
    private Timestamp createTime;

    // UpdateTime
    private Timestamp updateTime;

}
