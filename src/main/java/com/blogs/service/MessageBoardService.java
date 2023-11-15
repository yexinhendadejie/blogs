package com.blogs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.page.PageMessageBoardDto;
import com.blogs.domain.vo.messageBoard.MessageBoardVo;

public interface MessageBoardService {

    // 查看当前账号所有的留言评论
    IPage<MessageBoardVo> findAllMessageBoard(PageMessageBoardDto pageMessageBoardDto);

    // 第一层 给对方留言板留言
    void sendMessageBoard(Integer toId, String content);

    // 删除留言
    void deleteMessageBoard(Integer id);

}
