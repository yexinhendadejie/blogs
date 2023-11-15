package com.blogs.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blogs.common.enums.MsgStatusEnum;
import com.blogs.common.enums.MsgTypeEnum;
import com.blogs.domain.dto.messageBoard.MessageBoardReplyDto;
import com.blogs.entity.MessageBoardReply;
import com.blogs.mapper.MessageBoardReplyMapper;
import com.blogs.service.MessageBoardReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageBoardReplyServiceImpl implements MessageBoardReplyService {

    @Resource
    MessageBoardReplyMapper messageBoardReplyMapper;

    @Override
    public List<MessageBoardReply> findAllMessageBoardReply(MessageBoardReplyDto messageBoardReplyDto) {
        // 根据外层id查询所有的回复
        List<MessageBoardReply> messageBoardReplyList = messageBoardReplyMapper.selectList(Wrappers.<MessageBoardReply>lambdaQuery()
                .eq(MessageBoardReply::getCommentId, messageBoardReplyDto.getCommentId())
                .eq(MessageBoardReply::getCurrentBoardId, messageBoardReplyDto.getCurrentBoardId())
                .eq(MessageBoardReply::getReplyType, MsgTypeEnum.REPLY.getId())
                .eq(MessageBoardReply::getStatus, MsgStatusEnum.REPLY_OUT.getId())
                .orderByAsc(MessageBoardReply::getCreateTime));
        return null;
    }
}
