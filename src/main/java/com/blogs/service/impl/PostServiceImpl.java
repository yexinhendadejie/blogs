package com.blogs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blogs.common.global.GlobalConstants;
import com.blogs.domain.dto.page.PagePostDto;
import com.blogs.entity.Post;
import com.blogs.mapper.PostMapper;
import com.blogs.service.PostService;

import javax.annotation.Resource;

public class PostServiceImpl implements PostService {

  @Resource
  private PostMapper postMapper;

  @Override
  public IPage<Post> findAllPost(PagePostDto pagePostDto) {
    Page<Post> page = new Page<>(pagePostDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);

    LambdaQueryWrapper<Post> taskLambdaQueryWrapper = Wrappers.<Post>lambdaQuery()
        .eq(Post::getUserId, pagePostDto.getUserId())

        .orderByDesc(Post::getCreateTime);
    return null;
  }
}
