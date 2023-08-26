package com.blogs.service;

import com.blogs.domain.dto.blog.DelBlogDto;
import com.blogs.domain.dto.page.PageCollectionDto;
import com.blogs.domain.vo.collection.CollectionVo;
import com.github.pagehelper.PageInfo;

public interface CollectionService {

    // 分页查询收藏
    PageInfo<CollectionVo> findAllCollection(PageCollectionDto pageCollectionDto);


    // 删除收藏
    void deleteCollection(DelBlogDto delBlogDto);
}
