package com.blogs.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blogs.common.enums.MsgTypeEnum;
import com.blogs.common.global.GlobalConstants;
import com.blogs.domain.dto.page.PageMessageBoardDto;
import com.blogs.domain.vo.messageBoard.MessageBoardVo;
import com.blogs.entity.MessageBoard;
import com.blogs.mapper.MessageBoardMapper;
import com.blogs.service.MessageBoardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageBoardServiceImpl implements MessageBoardService {
    @Resource
    MessageBoardMapper messageBoardMapper;

    @Override
    public IPage<MessageBoardVo> findAllMessageBoard(PageMessageBoardDto pageMessageBoardDto) {
        Page<MessageBoard> page = new Page<>(pageMessageBoardDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);

        // 进行分页查询，传入分页对象和查询条件
        IPage<MessageBoard> messageBoardIPage = messageBoardMapper.selectPage(page, Wrappers.<MessageBoard>lambdaQuery()
                .eq(MessageBoard::getFindAccountBoard, pageMessageBoardDto.getFindAccountBoard())
                .eq(MessageBoard::getToId, pageMessageBoardDto.getToId())
                .eq(MessageBoard::getMsgType, MsgTypeEnum.COMMENT.getId())
                .orderByAsc(MessageBoard::getCreateTime));

        // 手动分页查询
        List<MessageBoard> messageBoardList = messageBoardMapper.selectAllAndFromId(
                pageMessageBoardDto.getFindAccountBoard(),
                pageMessageBoardDto.getToId(),
                pageMessageBoardDto.getMsgType(),
                (pageMessageBoardDto.getPageNum() - 1) * GlobalConstants.PAGE_SIZE_DEFAULT,
                GlobalConstants.PAGE_SIZE_DEFAULT);

        // 将查询结果转换为 MessageBoardVo 对象列表
        List<MessageBoardVo> messageBoardVoList = CglibUtil.copyList(messageBoardList, MessageBoardVo::new);

        // 创建 MyBatis Plus 的分页对象，并将查询结果设置进去
        IPage<MessageBoardVo> pageInfo = new Page<>(pageMessageBoardDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);
        pageInfo.setRecords(messageBoardVoList);
        pageInfo.setTotal(messageBoardIPage.getTotal());

        return pageInfo;
    }

    @Override
    public void sendMessageBoard(Integer toId, String content) {
        MessageBoard messageBoard = new MessageBoard();
        messageBoard.setFromId(StpUtil.getLoginIdAsInt());
        messageBoard.setToId(toId);
        messageBoard.setContent(content);
        messageBoard.setMsgType(MsgTypeEnum.COMMENT.getId());
        messageBoard.setFindAccountBoard(toId);
        messageBoardMapper.insert(messageBoard);
    }

    @Override
    public void deleteMessageBoard(Integer id) {
        messageBoardMapper.deleteById(id);
    }

}
