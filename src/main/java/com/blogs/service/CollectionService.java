package com.blogs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.page.PageCollectionDto;
import com.blogs.domain.vo.collection.CollectionVo;

public interface CollectionService {

    // 分页查询收藏
    IPage<CollectionVo> findAllCollection(PageCollectionDto pageCollectionDto);

    // 收藏博客
    String collectionBlog(Integer blogId);

    // 软取消收藏
}
