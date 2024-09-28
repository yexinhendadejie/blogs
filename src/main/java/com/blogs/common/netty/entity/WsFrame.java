package com.blogs.common.netty.entity;

import com.blogs.common.enums.ImEnum;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
// 链式调用
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WsFrame {
    private Boolean success;
    private String message;
    // 业务策略
    private String strategy;
    // 消息体
    private String content;
    // 消息类型
    private String type;
    // 消息发送者
    private Integer sender;
    // 消息接收者
    private Integer receiver;

    private Long pushTime;

    private Long sendTime;


    public static WsFrame ok(String message) {
        WsFrame frame = new WsFrame();
        frame.success = true;
        frame.message = message;
        frame.pushTime = System.currentTimeMillis();
        frame.sender = 0;
        frame.sendTime = System.currentTimeMillis();
        return frame;
    }

    public static WsFrame fail(String message) {
        WsFrame frame = new WsFrame();
        frame.message = message;
        frame.success = false;
        frame.strategy = ImEnum.Strategy.FAIL.getValue();
        frame.pushTime = System.currentTimeMillis();
        frame.sender = 0;
        frame.sendTime = System.currentTimeMillis();
        return frame;
    }

    public void to(Channel channel) {
        channel.writeAndFlush(this);
    }


}
