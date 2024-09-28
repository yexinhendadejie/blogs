package com.blogs.common.netty.handle.codec;

import com.blogs.common.netty.entity.WsFrame;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

public class WsFrameWebSocketFrameCodec extends MessageToMessageCodec<TextWebSocketFrame, WsFrame> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, WsFrame wsFrame, List<Object> list) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(wsFrame);
        list.add(new TextWebSocketFrame(json));
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame, List<Object> list) throws Exception {
        String json = textWebSocketFrame.text();
        ObjectMapper objectMapper = new ObjectMapper();
        WsFrame wsFrame = objectMapper.readValue(json, WsFrame.class);
        list.add(wsFrame);
    }
}
