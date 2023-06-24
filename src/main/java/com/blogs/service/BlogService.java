package com.blogs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.page.PagePostDto;
import com.blogs.domain.dto.post.BlogDto;
import com.blogs.domain.vo.BlogVo;

public interface BlogService {

  // 查看所有博客
  IPage<BlogVo> findAllPost(PagePostDto pagePostDto);


  // 根据title查询博客
  IPage<BlogVo> findByTitle(PagePostDto pagePostDto);

  // 添加博客
  void insertBlog(BlogDto postDto);

  // 删除博客
  void deleteBlog(Integer id);

  // 浏览量增加
  void visit(Integer id);

}
