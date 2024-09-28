package com.blogs.common.netty.strategy;

import com.blogs.common.anno.ImStrategy;
import com.blogs.common.enums.ImEnum;
import com.blogs.common.netty.config.DefaultAttributes;
import com.blogs.common.netty.entity.AbstractWsStrategy;
import com.blogs.common.netty.entity.WsFrame;
import com.blogs.utils.heartbeat.HeartBeatManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

// 心跳监控
@Slf4j
@Component
@ImStrategy(ImEnum.Strategy.HEART_BEAT)
public class HeartBeatMonitor extends AbstractWsStrategy {

    /**
     * 消费者内部
     */
    // 管理各种心跳包
    // 用户在聊天页面的心跳
    public final static HeartBeatManager<Integer, WsFrame> USER_IN_CHAT_PAGE_HEARTBEAT = new HeartBeatManager<>(1000 * 20);

    @AllArgsConstructor
    @Getter
    public enum HeartBeat {
        USER_IN_CHAT_PAGE("用户在聊天页面的心跳", (userId, wsFrame) -> {
            // 清除所有已过期的心跳
            USER_IN_CHAT_PAGE_HEARTBEAT.clearDeath();
            // 刷新心跳 动态地提供当前的 channel 作为新的元素
            // 每次调用 refresh 方法时，() -> channel 都会返回当前的 channel 对象。
            USER_IN_CHAT_PAGE_HEARTBEAT.refresh(userId, () -> wsFrame);
        });
        private final String desc;
        // 消费心跳的方式
        private final BiConsumer<Integer, WsFrame> consumer;
    }

    /**
     * 消费者外部 实现业务逻辑
     */
    // 收到心跳框架会调用 handle 方法 例如 {"type":"USER_IN_CHAT_PAGE","strategy":"HEART_BEAT"}
    @Override
    public void handle(ChannelHandlerContext ctx, WsFrame frame) {
        // 心跳监控
        log.info("[im] 心跳监控: {}", frame);
        // 发送所需包
        // 设置发送者发送时间
        // 获取发送者拆
        Integer senderId = DefaultAttributes.getUserId(ctx.channel());
        log.info("发送者的id: {}: ", senderId);
        if (senderId == null) {
            log.error("[im] 心跳监控失败: 未获取到发送者id");
            return;
        }
        // 获取心跳类型 USER_IN_CHAT_PAGE_HEARTBEAT
        String type = frame.getType();
        // 从心跳枚举中获取心跳类型
        HeartBeat heartBeat = null;
        try {
            heartBeat = HeartBeat.valueOf(type);
        } catch (IllegalArgumentException e) {
            log.error("[im] 心跳类型不存在: {}", type);
            return;
        }
        // 消费心跳
        try {
            heartBeat.getConsumer().accept(senderId, frame);
            WsFrame.ok("心跳监控成功").setPushTime(System.currentTimeMillis()).to(ctx.channel());
        } catch (Exception e) {
            WsFrame.fail("心跳监控失败").setPushTime(System.currentTimeMillis()).to(ctx.channel());
            e.printStackTrace();
            log.error("[im] 心跳消费失败: {}", e.getMessage());
        }
    }

    public static void clearAllHeatBeatForUser(Integer userId) {
        USER_IN_CHAT_PAGE_HEARTBEAT.delete(userId);
    }
}
