package com.blogs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blogs.entity.Msg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MsgMapper extends BaseMapper<Msg> {

    // 查询数据库所有未读消息
    List<Msg> selectUnreadMsgCount();
}
