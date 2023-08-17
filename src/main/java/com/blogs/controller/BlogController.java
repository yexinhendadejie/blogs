package com.blogs.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.blog.BlogDto;
import com.blogs.domain.dto.page.PageBlogDto;
import com.blogs.domain.vo.BlogVo;
import com.blogs.service.BlogService;
import com.blogs.utils.Resp;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    // 查看所有博客
    @PostMapping("/findAllPost")
    public Resp<IPage<BlogVo>> findAllPost(@Validated @RequestBody PageBlogDto pageBlogDto) {
        return Resp.ok(blogService.findAllPost(pageBlogDto));
    }

    // 根据title查询博客
    @PostMapping("/findByTitle")
    public Resp<IPage<BlogVo>> findByTitle(@Validated @RequestBody PageBlogDto pageBlogDto) {
        return Resp.ok(blogService.findByTitle(pageBlogDto));
    }

    // 添加博客
    @PostMapping("/insertBlog")
    public Resp<Void> insertBlog(@Validated @RequestBody BlogDto postDto) {
        blogService.insertBlog(postDto);
        return Resp.ok().msg("成功添加博客！");
    }

    // 软删除博客
    @DeleteMapping("/deleteBlog/{id}")
    public Resp<Void> deleteBlog(@PathVariable Integer id) {
        blogService.deleteBlog(id);
        return Resp.ok().msg("删除成功！");
    }

    // 硬删除博客
    @DeleteMapping("/deleteBlogHard")
    public Resp<Void> deleteBlogHard(@RequestBody List<Integer> blogIds) {
        blogService.deleteBlogHard(blogIds);
        return Resp.ok().msg("删除成功！");
    }

    // 浏览量增加
    @GetMapping("/visit/{id}")
    public Resp<Void> visit(@PathVariable Integer id) {
        blogService.visit(id);
        return Resp.ok().msg("增加成功！");
    }

    // 查找所有软删除博客
    @PostMapping("/findAllPostDel")
    public Resp<IPage<BlogVo>> findAllPostDel(@Validated @RequestBody PageBlogDto pageBlogDto) {
        return Resp.ok(blogService.findAllPostDel(pageBlogDto));
    }

    // 恢复博客
    @GetMapping("/recoverBlog/{id}")
    public Resp<Void> recoverBlog(@PathVariable @RequestBody Integer id) {
        blogService.recoverBlog(id);
        return Resp.ok().msg("恢复成功！");
    }

    // 查找单个博客
    @GetMapping("/findOne/{id}")
    public Resp<BlogVo> findOne(@PathVariable Integer id) {
        return Resp.ok(blogService.findOne(id));
    }

    // 修改博客
    @PostMapping("/updateBlog")
    public Resp<Void> updateBlog(@Validated @RequestBody BlogDto blogDto) {
        blogService.updateBlog(blogDto);
        return Resp.ok().msg("修改成功！");
    }

    // 点赞
    @GetMapping("/support/{id}")
    public Resp<Void> support(@PathVariable Integer id) {
        blogService.support(id);
        return Resp.ok().msg("点赞成功！");
    }

    // 已经点赞但是取消了
    @GetMapping("/supportCancel/{id}")
    public Resp<Void> supportCancel(@PathVariable Integer id) {
        blogService.supportCancel(id);
        return Resp.ok().msg("取消点赞成功！");
    }

    // 踩
    @GetMapping("/down/{id}")
    public Resp<Void> down(@PathVariable Integer id) {
        blogService.down(id);
        return Resp.ok().msg("踩成功！");
    }

    // 已经踩但是取消了
    @GetMapping("/downCancel/{id}")
    public Resp<Void> downCancel(@PathVariable Integer id) {
        blogService.downCancel(id);
        return Resp.ok().msg("取消踩成功！");
    }
}