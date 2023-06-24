package com.blogs.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.page.PagePostDto;
import com.blogs.domain.dto.post.BlogDto;
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
  private BlogService postService;

  // 查看所有博客
  @PostMapping("/findAllPost")
  public Resp<IPage<BlogVo>> findAllPost(@Validated @RequestBody PagePostDto pagePostDto) {
    return Resp.ok(postService.findAllPost(pagePostDto));
  }

  // 根据title查询博客
  @PostMapping("/findByTitle")
  public Resp<IPage<BlogVo>> findByTitle(@Validated @RequestBody PagePostDto pagePostDto) {
    return Resp.ok(postService.findByTitle(pagePostDto));
  }

  // 添加博客
  @PostMapping("/insertBlog")
  public Resp<Void> insertBlog(@Validated @RequestBody BlogDto postDto) {
    postService.insertBlog(postDto);
    return Resp.ok().msg("添加成功！");
  }

  //删除博客
  @GetMapping("/deleteBlog/{id}")
  public Resp<Void> deleteBlog(@PathVariable Integer id) {
    postService.deleteBlog(id);
    return Resp.ok().msg("删除成功！");
  }

  // 浏览量增加
  @GetMapping("/visit/{id}")
  public Resp<Void> visit(@PathVariable Integer id) {
    postService.visit(id);
    return Resp.ok().msg("增加成功！");
  }
}
