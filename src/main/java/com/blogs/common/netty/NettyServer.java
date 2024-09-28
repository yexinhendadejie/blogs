package com.blogs.common.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Slf4j
@Component
public class NettyServer {
    EventLoopGroup bossGroup = new NioEventLoopGroup(2);
    EventLoopGroup workGroup = new NioEventLoopGroup(2);
    @Resource
    private ServerChannelInitializer serverChannelInitializer;

    public void start(Integer port) {
        log.info("NettyServer 启动...");
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(this.bossGroup, this.workGroup)
                // 设置服务器的通信模式为 NIO
                .channel(NioServerSocketChannel.class)
                // 每个连接接受时会执行的逻辑
                .childHandler(this.serverChannelInitializer)
                // 设置队列大小
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 用于设置 TCP 连接是否开启 TCP KeepAlive。
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler(LogLevel.INFO));
        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("服务器启动开始监听端口: {}", port);
            future.channel().closeFuture().sync();
            if (future.isSuccess()) {
                log.info("启动 Netty Server");
            }
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }

    }

    @PreDestroy
    public void destroy() {
        this.bossGroup.shutdownGracefully();
        this.workGroup.shutdownGracefully();
        log.info("关闭Netty");
    }
}