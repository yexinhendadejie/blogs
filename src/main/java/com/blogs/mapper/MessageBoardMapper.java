package com.blogs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blogs.entity.MessageBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageBoardMapper extends BaseMapper<MessageBoard> {

    List<MessageBoard> selectAllAndFromId(@Param("findAccountBoard") int findAccountBoard,
                                          @Param("toId") int toId,
                                          @Param("msgType") int msgType,
                                          @Param("startRow") int startRow,
                                          @Param("pageSize") int pageSize);
}
