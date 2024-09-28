package com.blogs.common.netty.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public abstract class AbstractWsStrategy implements IMStrategyInter<WsFrame> {
    protected String strategy;

    @Override
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    @Override
    public String getStrategy() {
        return this.strategy;
    }
}
