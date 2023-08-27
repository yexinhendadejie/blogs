package com.blogs.controller;

import com.blogs.domain.dto.blog.DelBlogDto;
import com.blogs.domain.dto.page.PageCollectionDto;
import com.blogs.domain.vo.collection.CollectionVo;
import com.blogs.service.CollectionService;
import com.blogs.utils.Resp;
import com.github.pagehelper.PageInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Resource
    CollectionService collectionService;

    // 查找所有博客
    @PostMapping("/findAllCollection")
    public Resp<PageInfo<CollectionVo>> findAllCollection(@Validated @RequestBody PageCollectionDto pageCollectionDto) {
        return Resp.ok(collectionService.findAllCollection(pageCollectionDto));
    }

    // 收藏博客
    @PostMapping("/collectionBlog/{blogId}")
    public Resp<Void> collectionBlog(@PathVariable Integer blogId) {
        collectionService.collectionBlog(blogId);
        return Resp.ok().msg("收藏成功");
    }

    // 取消收藏博客
    @PostMapping("/cancelCollection/{blogId}")
    public Resp<Void> cancelCollection(@PathVariable Integer blogId) {
        collectionService.cancelCollection(blogId);
        return Resp.ok().msg("取消收藏成功");
    }

    // 删除收藏
    @DeleteMapping("/deleteCollection")
    public Resp<Void> deleteCollection(@Validated @RequestBody DelBlogDto delBlogDto) {
        collectionService.deleteCollection(delBlogDto);
        return Resp.ok().msg("删除成功");
    }
}
