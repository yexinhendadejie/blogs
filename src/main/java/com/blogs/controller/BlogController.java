package com.blogs.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.blog.DelBlogDto;
import com.blogs.domain.dto.page.PageBlogDto;
import com.blogs.domain.dto.blog.BlogDto;
import com.blogs.domain.vo.BlogVo;
import com.blogs.service.BlogService;
import com.blogs.utils.Resp;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

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
    return Resp.ok().msg("添加成功！");
  }

  // 软删除博客
  @DeleteMapping("/deleteBlog/{id}")
  public Resp<Void> deleteBlog(@PathVariable Integer id) {
    blogService.deleteBlog(id);
    return Resp.ok().msg("删除成功！");
  }
  // 硬删除博客
  @DeleteMapping("/deleteBlogHard")
  public Resp<Void> deleteBlogHard(@Validated @RequestBody DelBlogDto delBlogDto) {
    blogService.deleteBlogHard(delBlogDto);
    return Resp.ok().msg("删除成功！");
  }

  // 浏览量增加
  @GetMapping("/visit/{id}")
  public Resp<Void> visit(@PathVariable Integer id) {
    blogService.visit(id);
    return Resp.ok().msg("增加成功！");
  }
}
