package com.blogs.common.netty.strategy;

import cn.hutool.json.JSONUtil;
import com.blogs.common.anno.ImStrategy;
import com.blogs.common.enums.ImEnum;
import com.blogs.common.netty.config.DefaultAttributes;
import com.blogs.common.netty.entity.AbstractWsStrategy;
import com.blogs.common.netty.entity.WsFrame;
import com.blogs.entity.Msg;
import com.blogs.mapper.MsgMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.blogs.common.netty.config.DefaultAttributes.CHANNEL_GROUP;
import static com.blogs.common.netty.config.DefaultAttributes.USER_ID_GROUP;

@Slf4j
@Component
@ImStrategy(ImEnum.Strategy.PRIVATE_CHAT)
public class InPrivateChatStrategy extends AbstractWsStrategy {

    @Resource
    private MsgMapper msgMapper;

    @Override
    public void handle(ChannelHandlerContext ctx, WsFrame frame) {
        log.info("[im] 私聊: {}", frame);
        Integer receiver = frame.getReceiver();
        log.info("接收者id: {}", receiver);

        WsFrame wsFrameInHeartBeat = HeartBeatMonitor.USER_IN_CHAT_PAGE_HEARTBEAT.getHeartBeat(receiver);
        Channel channel = DefaultAttributes.getChannel(receiver);
        if (channel == null) {
            log.info("用户不在线");
            saveMsgToDB(frame);
            return;
        }

        if (wsFrameInHeartBeat != null && wsFrameInHeartBeat.getReceiver() != null
                && wsFrameInHeartBeat.getReceiver().equals(DefaultAttributes.getUserId(ctx.channel()))) {
            // 用户在聊天页面,直接推送
            WsFrame.ok("新消息")
                    .setStrategy(getStrategy())
                    .setSender(frame.getSender())
                    .setReceiver(frame.getReceiver())
                    .setContent(frame.getContent())
                    .to(channel);
            return;
        }
        // 用户在线但是不在聊天页面
        WsFrame.ok("你在线但是不在聊天页面的消息提示")
                .setContent(JSONUtil.toJsonStr(frame))
                .setStrategy(ImEnum.Notify.USER.getValue())
                .setSender(frame.getSender())
                .setReceiver(receiver)
                .to(channel);
        // 用户不在线
        // 保存消息到数据库
        saveMsgToDB(frame);
    }

    private void saveMsgToDB(WsFrame frame) {
        Msg msg = new Msg();
        msg.setSenderId(frame.getSender());
        msg.setReceiverId(frame.getReceiver());
        msg.setContent(frame.getContent());
        // 私聊自己塞到数据库的
        msg.setMsgType(1);
        msgMapper.insert(msg);
    }

    private Channel getUserChannel(Integer userId) {
        return CHANNEL_GROUP.stream()
                .filter(channel -> userId.equals(channel.attr(USER_ID_GROUP).get()))
                .findAny()
                .orElse(null);
    }
}
