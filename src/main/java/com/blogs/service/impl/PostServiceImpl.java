package com.blogs.service.impl;

import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blogs.common.global.GlobalConstants;
import com.blogs.domain.dto.page.PagePostDto;
import com.blogs.domain.vo.PostVo;
import com.blogs.entity.Post;
import com.blogs.mapper.PostMapper;
import com.blogs.service.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PostServiceImpl implements PostService {

  @Resource
  private PostMapper postMapper;

  // 查看所有博客
  @Override
  public IPage<PostVo> findAllPost(PagePostDto pagePostDto) {
    Page<Post> page = new Page<>(pagePostDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);

    IPage<Post> postPage = postMapper.selectPage(page,Wrappers.<Post>query().like("user_id",
        pagePostDto.getUserId())
    );

    return postPage.convert(blog -> CglibUtil.copy(blog, PostVo.class));
  }

  @Override
  public IPage<PostVo> findByTitle(PagePostDto pagePostDto) {





    return null;
  }

  @Override
  public void insertBlog() {

  }


}
