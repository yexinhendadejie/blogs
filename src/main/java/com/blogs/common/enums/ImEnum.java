package com.blogs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface ImEnum {
    @AllArgsConstructor
    @Getter
    enum Strategy {
        // 业务策略
        GLOBAL("global", "全局"),
        PRIVATE_CHAT("msg_private_chat", "私聊"),
        // 心跳策略
        HEART_BEAT("heart_beat", "心跳"),
        FAIL("fail", "失败"),
        GROUP_CHAT("msg_group_chat", "群聊"),
        CHAT_ROOM("msg_chatroom", "聊天室"),
        PING("ping", "ping");

        private final String value;
        private final String desc;
    }

    @AllArgsConstructor
    @Getter
    enum Notify {

        SYSTEM("notify_system", "系统通知"),
        USER("notify_user", "用户通知");

        private final String value;
        private final String desc;
    }
}
