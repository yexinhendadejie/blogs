package com.blogs.common.netty.strategy;

import com.blogs.common.anno.ImStrategy;
import com.blogs.common.enums.ImEnum;
import com.blogs.common.netty.entity.AbstractWsStrategy;
import com.blogs.common.netty.entity.WsFrame;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ImStrategy(ImEnum.Strategy.GLOBAL)
public class GlobalStrategy extends AbstractWsStrategy {
    @Override
    public void handle(ChannelHandlerContext ctx, WsFrame frame) {
        log.info("[im] 全局通知策略: {}", frame);
        WsFrame.fail("未知策略: " + frame.getStrategy()).to(ctx.channel());
    }
}
