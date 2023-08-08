package com.blogs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.blog.DelBlogDto;
import com.blogs.domain.dto.page.PageBlogDto;
import com.blogs.domain.dto.blog.BlogDto;
import com.blogs.domain.vo.BlogVo;
import org.springframework.transaction.annotation.Transactional;

public interface BlogService {

  // 查看所有博客
  IPage<BlogVo> findAllPost(PageBlogDto pageBlogDto);


  // 根据title查询博客
  IPage<BlogVo> findByTitle(PageBlogDto pageBlogDto);

  // 添加博客
  void insertBlog(BlogDto postDto);

  // 软删除博客
  void deleteBlog(Integer id);

  // 硬删除博客
   void deleteBlogHard(DelBlogDto delBlogDto);

  // 浏览量增加
  void visit(Integer id);

}
