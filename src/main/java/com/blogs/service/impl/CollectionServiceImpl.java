package com.blogs.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blogs.common.global.GlobalConstants;
import com.blogs.domain.dto.page.PageCollectionDto;
import com.blogs.domain.vo.collection.CollectionVo;
import com.blogs.entity.Collection;
import com.blogs.mapper.CollectionMapper;
import com.blogs.service.CollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Resource
    private CollectionMapper collectionMapper;

    @Override
    public IPage<CollectionVo> findAllCollection(PageCollectionDto pageCollectionDto) {
        Page<Collection> page = new Page<>(pageCollectionDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);

        // 进行分页查询，传入分页对象和查询条件
        IPage<Collection> collectionIPage = collectionMapper.selectPage(page, Wrappers.<Collection>lambdaQuery()
                .eq(Collection::getUserId, StpUtil.getLoginIdAsInt())
                .orderByDesc(Collection::getCreateTime));

        // 手动进行分页查询，调用自己的手写 SQL 查询 selectByUserId
        List<Collection> collectionList = collectionMapper.selectByUserId(
                StpUtil.getLoginIdAsInt(),
                (pageCollectionDto.getPageNum() - 1) * GlobalConstants.PAGE_SIZE_DEFAULT,
                GlobalConstants.PAGE_SIZE_DEFAULT);

        // 将查询结果转换为 CollectionVo 对象列表
        List<CollectionVo> collectionVoList = CglibUtil.copyList(collectionList, CollectionVo::new);

        // 创建 MyBatis Plus 的分页对象，并将查询结果设置进去
        IPage<CollectionVo> pageInfo = new Page<>(pageCollectionDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);
        pageInfo.setRecords(collectionVoList);
        pageInfo.setTotal(collectionIPage.getTotal());

        return pageInfo;

    }

    @Override
    public String collectionBlog(Integer blogId) {
        Collection selCollection = collectionMapper.selectOne(Wrappers.<Collection>lambdaQuery()
                .eq(Collection::getUserId, StpUtil.getLoginIdAsInt())
                .eq(Collection::getTargetId, blogId));
        if (selCollection != null) {
            // 就取消收藏博客
            collectionMapper.delete(Wrappers.<Collection>lambdaQuery()
                    .eq(Collection::getUserId, StpUtil.getLoginIdAsInt())
                    .eq(Collection::getTargetId, blogId));
            return "取消收藏成功";
        }

        Collection collection = new Collection();
        collection.setTargetId(blogId);
        // 收藏博客为1
        collection.setIsCollection(true);
        collection.setUserId(StpUtil.getLoginIdAsInt());
        collectionMapper.insert(collection);
        return "收藏成功";
    }
    //
}
// 分页查询
//        Page<Collection> page = new Page<>(pageCollectionDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);
//        // 按照时间排序
//        Page<Collection> postPage = collectionMapper.selectPage(page, Wrappers.<Collection>lambdaQuery()
//                .eq(Collection::getUserId, StpUtil.getLoginIdAsInt())
//                .orderByDesc(Collection::getCreateTime)
//        );
//        return postPage.convert(collection -> CglibUtil.copy(collection, CollectionVo.class));

