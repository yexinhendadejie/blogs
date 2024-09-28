package com.blogs.common.netty.handle;

import cn.dev33.satoken.stp.StpUtil;
import com.blogs.common.netty.config.DefaultAttributes;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 登录认证Handle
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class LoginAuthRespHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        // 传递给下一个Handler
        channelHandlerContext.fireChannelRead(textWebSocketFrame.retain());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            log.info("握手成功,进入身份校验");
//            HttpHeaders entries = ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestHeaders();
//            String token = entries.get("authorization");

            // 获取握手请求的URL
            String requestUri = ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestUri();
            // 使用QueryStringDecoder解析URL
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(requestUri, CharsetUtil.UTF_8);
            Map<String, List<String>> parameters = queryStringDecoder.parameters();
            System.out.println(parameters);
            // 获取authorization参数值
            String token = parameters.get("authorization").get(0);

            log.info("token:{}", token);
            if (token == null) {
                log.error("token为空，关闭连接");
                // 移除token,user_id
                ctx.channel().close();
                return;
            }

            Object loginId = StpUtil.getLoginIdByToken(token);
            if (loginId == null) {
                log.error("token无效，关闭连接");
                ctx.channel().close();
                return;
            }

            // 获取用户id
            Integer userId = Integer.valueOf(loginId.toString());
            // 每个channel都有id，asLongText是全局channel唯一id
            Channel channel = ctx.channel();
            // 存储channel的id和用户的主键
            DefaultAttributes.removeChannel(userId);
            DefaultAttributes.CHANNEL_GROUP.add(ctx.channel());
            // 设置属性
            channel.attr(DefaultAttributes.TOKEN_GROUP).set(token);
            channel.attr(DefaultAttributes.USER_ID_GROUP).set(userId);

            log.info("用户id:{},token:{},channelId:{}", userId, token, channel);
        }

        // 如果已经握手成功，继续处理其他的Handler
        super.userEventTriggered(ctx, evt);
    }
}