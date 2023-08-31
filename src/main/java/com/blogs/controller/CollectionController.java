package com.blogs.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.page.PageCollectionDto;
import com.blogs.domain.vo.collection.CollectionVo;
import com.blogs.service.CollectionService;
import com.blogs.utils.Resp;
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
    public Resp<IPage<CollectionVo>> findAllCollection(@Validated @RequestBody PageCollectionDto pageCollectionDto) {
        return Resp.ok(collectionService.findAllCollection(pageCollectionDto));
    }

    // 收藏/ 取消博客
    @PostMapping("/collectionBlog/{blogId}")
    public Resp<String> collectionBlog(@PathVariable Integer blogId) {
        String msg = collectionService.collectionBlog(blogId);
        return Resp.ok(msg);
    }
}
