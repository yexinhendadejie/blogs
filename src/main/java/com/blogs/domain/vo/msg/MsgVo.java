package com.blogs.domain.vo.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgVo {

    // 发送者
    private Integer senderId;


    // 消息内容
    private Integer count;


}
