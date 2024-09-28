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
@ImStrategy(ImEnum.Strategy.PING)
public class PingStrategy extends AbstractWsStrategy {
    @Override
    public void handle(ChannelHandlerContext ctx, WsFrame frame) {
        WsFrame.ok("连通性良好").setStrategy(getStrategy()).setContent("PONG").to(ctx.channel());
    }
}
