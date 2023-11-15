package com.blogs.service;

import com.blogs.domain.dto.messageBoard.MessageBoardReplyDto;
import com.blogs.entity.MessageBoardReply;

import java.util.List;

public interface MessageBoardReplyService {


    // 第一层根据留言id查询所有的回复
    List<MessageBoardReply> findAllMessageBoardReply(MessageBoardReplyDto messageBoardReplyDto);

}
