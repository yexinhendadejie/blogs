package com.blogs.common.netty.handle;


import com.blogs.common.anno.ImStrategy;
import com.blogs.common.enums.ImEnum;
import com.blogs.common.netty.config.DefaultAttributes;
import com.blogs.common.netty.entity.IMStrategyInter;
import com.blogs.common.netty.entity.WsFrame;
import com.blogs.common.netty.strategy.HeartBeatMonitor;
import com.blogs.common.netty.strategy.InPrivateChatStrategy;
import com.blogs.common.netty.strategy.PingStrategy;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

// 策略分发
@Slf4j
@Component
@ChannelHandler.Sharable
public class StrategyDispatcher extends SimpleChannelInboundHandler<WsFrame> {

    @Resource
    private ApplicationContext applicationContext;
    private final static Map<String, IMStrategyInter<WsFrame>> strategyMap = new HashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WsFrame msg) throws Exception {
        String strategy = msg.getStrategy();
        log.info("[im] 策略分发: {}", strategy);

        // 选择策略
        IMStrategyInter<WsFrame> imStrategyInter = pick(strategy);
        if (imStrategyInter == null) {
            WsFrame sendFrame = new WsFrame();
            log.error("[im] 策略分发失败: {}", strategy);
            sendFrame.fail("未知策略: " + strategy).to(ctx.channel());
            return;
        }
        imStrategyInter.handle(ctx, msg);
        log.info("[im] 策略分发完成: {}", strategy);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("用户下线: {}", DefaultAttributes.getUserId(ctx.channel()));
        super.channelInactive(ctx);
        // 移除信道
        DefaultAttributes.removeChannel(ctx.channel());
    }

    // 初始化策略
    @PostConstruct
    public void initStrategy() {
        log.info("[im] 初始化策略");
        //        addStrategy(PrivateChat.class);
        //        addStrategy(Chatroom.class);
        addStrategy(HeartBeatMonitor.class);
        //        addStrategy(PingPong.class);
        addStrategy(InPrivateChatStrategy.class);
        addStrategy(PingStrategy.class);
        log.info("[im] 初始化策略完成");
        log.info("[im] 策略集合: {}", strategyMap);
    }

    private void addStrategy(Class<? extends IMStrategyInter<WsFrame>> imStrategyInter) {
        ImStrategy annotation = imStrategyInter.getAnnotation(ImStrategy.class);
        log.info("[im] 注册策略: {}", annotation);
        if (annotation == null) {
            return;
        }
        ImEnum.Strategy strategyEnum = annotation.value();
        String strategy = strategyEnum.getValue();
        IMStrategyInter<WsFrame> instance = applicationContext.getBean(imStrategyInter);
        instance.setStrategy(strategy);
        strategyMap.put(strategy, instance);
    }

    // 选择策略
    private IMStrategyInter<WsFrame> pick(String strategy) {
        return strategyMap.get(strategy);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("[im] 策略分发异常", cause);
        super.exceptionCaught(ctx, cause);
        DefaultAttributes.removeChannel(ctx.channel());

    }
}

