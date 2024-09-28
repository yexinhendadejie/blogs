package com.blogs.common.netty.config;


import com.blogs.common.netty.entity.WsFrame;
import com.blogs.common.netty.strategy.HeartBeatMonitor;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DefaultAttributes {
    // 信道组
    public static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // token组
    public static final AttributeKey<String> TOKEN_GROUP = AttributeKey.valueOf("token");
    // 用户组
    public static final AttributeKey<Integer> USER_ID_GROUP = AttributeKey.valueOf("userId");


    // 释放某个信道(通过channel)
    public static void removeChannel(Channel channel) {
        Integer userId = channel.attr(USER_ID_GROUP).get();
        log.info("释放信道: 用户 {}", userId);
        // 移除信道
        HeartBeatMonitor.clearAllHeatBeatForUser(userId);
        CHANNEL_GROUP.remove(channel);
        // 移除相关属性值
        channel.attr(USER_ID_GROUP).set(null);
        channel.attr(TOKEN_GROUP).set(null);
        channel.close();
    }

    // 释放某个信道
    public static void removeChannel(Integer userId) {
        // 通过userId获取信道
        // 遍历信道组
        for (Channel channel : CHANNEL_GROUP) {
            Integer id = channel.attr(USER_ID_GROUP).get();
            if (id != null && id.equals(userId)) {
                removeChannel(channel);
                break;
            }
        }
    }

    // 发送消息
    public static void sendTo(Integer userId, WsFrame frame) {
        Channel channel = getChannel(userId);
        if (channel == null) {
            return;
        }
        channel.writeAndFlush(frame);
    }

    // 群发消息
    public static void send(List<Integer> userIds, WsFrame frame) {
        List<Channel> channels = listChannelByIds(userIds.toArray(new Integer[0]));
        if (channels == null || channels.isEmpty()) {
            return;
        }
        for (Channel channel : channels) {
            channel.writeAndFlush(frame);
        }
    }

    // 发送给所有
    public static void sendAll(WsFrame frame) {
        CHANNEL_GROUP.writeAndFlush(frame);
    }

    public static Channel getChannel(Integer userId) {
        for (Channel channel : CHANNEL_GROUP) {
            // 检查信道是否可用
            if (!channel.isActive()) {
                continue;
            }
            Integer id = channel.attr(USER_ID_GROUP).get();
            if (id != null && id.equals(userId)) {
                return channel;
            }
        }
        return null;
    }

    // 从channel中获取userId
    public static Integer getUserId(Channel channel) {
        return channel.attr(USER_ID_GROUP).get();
    }

    public static List<Channel> listChannelByIds(Integer... ids) {
        if (ids == null || ids.length == 0) {
            return null;
        }
        return CHANNEL_GROUP.stream().filter(channel -> {
            if (!channel.isActive()) {
                return false;
            }
            Integer id = channel.attr(USER_ID_GROUP).get();
            if (id == null) {
                return false;
            }
            for (Integer userId : ids) {
                if (userId.equals(id)) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }
}
