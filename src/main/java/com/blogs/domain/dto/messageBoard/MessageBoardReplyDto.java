package com.blogs.domain.dto.messageBoard;

import lombok.Data;

@Data
public class MessageBoardReplyDto {

    private Integer id;
    // 评论id
    private Integer commentId;

    // 回复目标id

    private Integer currentBoardId;

    // 回复类型
    private Integer replyType;

    // 回复内容
    private String content;

    // 回复用户id
    private Integer fromId;

    // 目标用户id
    private Integer toId;

    // 状态
    private Integer status;
}
