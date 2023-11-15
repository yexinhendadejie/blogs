package com.blogs.domain.vo.messageBoard;

import com.blogs.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBoardVo {

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

    private User toIdDetail;
    // CreateTime
    private Timestamp createTime;

    // UpdateTime
    private Timestamp updateTime;

}
