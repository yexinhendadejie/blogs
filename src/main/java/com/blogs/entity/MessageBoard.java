package com.blogs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBoard {


    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 表示评论人的id
    private Integer fromId;

    // 是评论目标人的id
    private Integer toId;

    // 内容
    private String content;

    // 类型
    private Integer msgType;

    // 需要查找的账号的留言板
    private Integer findAccountBoard;
    @TableField(exist = false)
    private User toIdDetail;

    // CreateTime
    private Timestamp createTime;

    // UpdateTime
    private Timestamp updateTime;

}
