package com.blogs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.page.PagePostDto;
import com.blogs.domain.vo.PostVo;
import com.blogs.entity.Post;

public interface PostService {

  // 查看所有博客
  IPage<PostVo> findAllPost(PagePostDto pagePostDto);

  // 根据title查询博客
  IPage<PostVo> findByTitle(PagePostDto pagePostDto);

  // 添加博客
  void insertBlog();



}
