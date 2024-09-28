package com.blogs.controller;

import com.blogs.service.MsgService;
import com.blogs.utils.Resp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/msg")
public class MsgController {

    @Resource
    private MsgService msgService;

    // 查询所有未读消息
    @RequestMapping("/selectUnreadMsgCount")
    public Resp selectUnreadMsgCount() {
        return Resp.ok(msgService.selectUnreadMsgCount());
    }
}
