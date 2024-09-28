package com.blogs.service.impl;

import com.blogs.domain.vo.msg.MsgVo;
import com.blogs.mapper.MsgMapper;
import com.blogs.service.MsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MsgServiceImpl implements MsgService {
    @Resource
    MsgMapper msgMapper;


    @Override
    public List<MsgVo> selectUnreadMsgCount() {
//        List<Msg> msgs = msgMapper.selectUnreadMsgCount();
//
//        // 遍历 msgs，计算每个 senderId 对应的计数值
//        for (Msg msg : msgs) {
//            Integer senderId = msg.getSenderId();
//            Integer count = msg.getCount();
//        }
//    }
        return null;
    }
}
