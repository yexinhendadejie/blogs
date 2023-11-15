package com.blogs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBoardReply {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    private Timestamp createTime;

    private Timestamp updateTime;

}
