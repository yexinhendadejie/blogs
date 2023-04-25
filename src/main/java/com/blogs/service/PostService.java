package com.blogs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.page.PagePostDto;
import com.blogs.entity.Post;

public interface PostService {

  // 查看所有博客
  IPage<Post> findAllPost(PagePostDto pagePostDto);
}
