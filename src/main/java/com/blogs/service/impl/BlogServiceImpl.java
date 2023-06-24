package com.blogs.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blogs.common.exception.ServiceException;
import com.blogs.common.global.GlobalConstants;
import com.blogs.domain.dto.page.PagePostDto;
import com.blogs.domain.dto.post.BlogDto;
import com.blogs.domain.vo.BlogVo;
import com.blogs.entity.Blog;
import com.blogs.mapper.BlogMapper;
import com.blogs.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BlogServiceImpl implements BlogService {

  @Resource
  private BlogMapper postMapper;

  // 查看所有博
  @Override
  public IPage<BlogVo> findAllPost(PagePostDto pagePostDto) {
    Page<Blog> page = new Page<>(pagePostDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);
    if (pagePostDto.getType().equals("最新")){
      // 按照时间排序
      Page<Blog> postPage = postMapper.selectPage(page, Wrappers.<Blog>lambdaQuery()
          .eq(Blog::getUserId, pagePostDto.getUserId())
          .orderByDesc(Blog::getCreateTime));

      return postPage.convert(blog -> CglibUtil.copy(blog, BlogVo.class));

    }else if (pagePostDto.getType().equals("热门")){
      // 按照点赞数排序
      Page<Blog> postPage = postMapper.selectPage(page, Wrappers.<Blog>lambdaQuery()
          .eq(Blog::getUserId, pagePostDto.getUserId())
          .orderByDesc(Blog::getSupport));

      return postPage.convert(blog -> CglibUtil.copy(blog, BlogVo.class));
    }else {
      throw new ServiceException("选择类型");
    }
  }

  // 根据title查询博客
  @Override
  public IPage<BlogVo> findByTitle(PagePostDto pagePostDto) {
    Page<Blog> page = new Page<>(pagePostDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);

    Page<Blog> postPage = postMapper.selectPage(page, Wrappers.<Blog>lambdaQuery()
        .eq(Blog::getUserId, pagePostDto.getUserId())
        .like(Blog::getTitle,pagePostDto.getTitle())
        .orderByDesc(Blog::getCreateTime));

    return postPage.convert(blog -> CglibUtil.copy(blog, BlogVo.class));
  }

  // 添加博客
  @Override
  public void insertBlog(BlogDto postDto) {
    if (postDto.getUserId() != StpUtil.getLoginIdAsInt()) throw new ServiceException("不是该用户");
    postMapper.insert(CglibUtil.copy(postDto, Blog.class));
  }

  // 删除博客
  @Override
  public void deleteBlog(Integer id){
    postMapper.deleteById(id);
  }

  // 浏览量增加
  @Override
  public void visit(Integer id) {
    // 获取当前blog
    Blog post = postMapper.selectOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, id));

    if(post == null) throw new ServiceException("该博客不存在！");
    post.setCollectionCount(post.getCollectionCount() + 1);
    // 浏览量增加
    postMapper.updateById(post);
  }




}
