package com.blogs.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.messageBoard.MessageBoardDto;
import com.blogs.domain.dto.page.PageMessageBoardDto;
import com.blogs.domain.vo.messageBoard.MessageBoardVo;
import com.blogs.service.MessageBoardService;
import com.blogs.utils.Resp;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/messageBoard")
public class MessageBoardController {

    @Resource
    private MessageBoardService messageBoardService;

    // 查看当前账号所有的留言
    @PostMapping("/findAllMessageBoard")
    public Resp<IPage<MessageBoardVo>> findAllMessageBoard(@Validated @RequestBody PageMessageBoardDto pageMessageBoardDto) {
        return Resp.ok(messageBoardService.findAllMessageBoard(pageMessageBoardDto));
    }

    // 给对方留言板留言
    @PostMapping("/sendMessageBoard")
    public Resp<Void> sendMessageBoard(@Validated @RequestBody MessageBoardDto messageBoardDto) {
        messageBoardService.sendMessageBoard(messageBoardDto.getToId(), messageBoardDto.getContent());
        return Resp.ok();
    }

    // 删除留言
    @DeleteMapping("/deleteMessageBoard/{id}")
    public Resp<Void> deleteMessageBoard(@PathVariable Integer id) {
        messageBoardService.deleteMessageBoard(id);
        return Resp.ok().msg("删除成功");
    }
}
