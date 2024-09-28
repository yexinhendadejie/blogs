package com.blogs.service;

import com.blogs.domain.vo.msg.MsgVo;

import java.util.List;

public interface MsgService {


    // 查询所有未读消息
    List<MsgVo> selectUnreadMsgCount();
}
