package com.blogs.common.netty;

import com.blogs.common.netty.handle.LoginAuthRespHandler;
import com.blogs.common.netty.handle.StrategyDispatcher;
import com.blogs.common.netty.handle.codec.WsFrameWebSocketFrameCodec;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Resource
    private LoginAuthRespHandler loginAuthRespHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //用户每次请求都会从第一个Handler开始
        // 解析Http请求
        pipeline.addLast(new HttpServerCodec());
        // 添加对读写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 添加对HTTP请求的聚合支持
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast("encoder", new StringDecoder(CharsetUtil.UTF_8));
        //定长解码器
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 100, 0, 2, 0, 2));
        //增加解码器
        pipeline.addLast(new WsFrameWebSocketFrameCodec());
        //这里设置读取报文的包头长度来避免粘包
        pipeline.addLast(new LengthFieldPrepender(2));
        // WebSocket 协议处理，/ws 是你的 WebSocket 地址
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536 * 10, false, true));

        //心跳续约
        pipeline.addLast(this.loginAuthRespHandler);

        pipeline.addLast(new StrategyDispatcher());
    }

}