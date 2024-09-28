package com.blogs.common.netty.entity;

import io.netty.channel.ChannelHandlerContext;

public interface IMStrategyInter<T> {

    void handle(ChannelHandlerContext ctx, T frame);

    void setStrategy(String strategy);

    String getStrategy();

}
