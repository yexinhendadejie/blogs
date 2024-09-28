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
public class Msg {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 发送者
    private Integer senderId;

    // 接收者
    private Integer receiverId;

    // 消息内容
    private String content;

    // 消息类型
    private Integer msgType;

    // 是否已读
    private Boolean isRead;

    private Timestamp createTime;

    private Timestamp updateTime;

}
