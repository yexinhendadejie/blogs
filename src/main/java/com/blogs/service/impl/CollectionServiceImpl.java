package com.blogs.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.cglib.CglibUtil;
import com.blogs.common.global.GlobalConstants;
import com.blogs.domain.dto.blog.DelBlogDto;
import com.blogs.domain.dto.page.PageCollectionDto;
import com.blogs.domain.vo.collection.CollectionVo;
import com.blogs.entity.Collection;
import com.blogs.mapper.CollectionMapper;
import com.blogs.service.CollectionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Resource
    private CollectionMapper collectionMapper;

    @Override
    public PageInfo<CollectionVo> findAllCollection(PageCollectionDto pageCollectionDto) {

        // 使用 PageHelper 开始分页
        Page page = PageHelper.startPage(pageCollectionDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);

        // 查询数据
        List<Collection> collectionList = collectionMapper.selectByUserId(StpUtil.getLoginIdAsInt());
        List<CollectionVo> collectionVo = CglibUtil.copyList(collectionList, CollectionVo::new);

        PageInfo<CollectionVo> pageInfo = new PageInfo<>(collectionVo);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageNum(page.getPageNum());
        pageInfo.setPageSize(GlobalConstants.RECRUITMENT_LIST_PAGE_SIZE);
        return pageInfo;
    }

    @Override
    public void deleteCollection(DelBlogDto delBlogDto) {
        collectionMapper.deleteByBlogId(delBlogDto.getBlogIds());
    }
}
// 分页查询
//        Page<Collection> page = new Page<>(pageCollectionDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);
//        // 按照时间排序
//        Page<Collection> postPage = collectionMapper.selectPage(page, Wrappers.<Collection>lambdaQuery()
//                .eq(Collection::getUserId, StpUtil.getLoginIdAsInt())
//                .orderByDesc(Collection::getCreateTime)
//        );
//        return postPage.convert(collection -> CglibUtil.copy(collection, CollectionVo.class));

