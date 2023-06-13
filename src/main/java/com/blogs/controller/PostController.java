package com.blogs.controller;

import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.page.PagePostDto;
import com.blogs.domain.dto.user.RegisterForEmailDto;
import com.blogs.domain.vo.PostVo;
import com.blogs.service.PostService;
import com.blogs.utils.Resp;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/post")
public class PostController {

  @Resource
  private PostService postService;

  // 查看所有博客
  @PostMapping("/findAllPostByTime")
  public Resp<IPage<PostVo>> findAllPostByTime(@Validated @RequestBody PagePostDto pagePostDto) {
    return Resp.ok(postService.findAllPostByTime(pagePostDto));
  }
}
