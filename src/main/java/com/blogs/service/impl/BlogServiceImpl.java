package com.blogs.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blogs.common.exception.ServiceException;
import com.blogs.common.global.GlobalConstants;
import com.blogs.domain.dto.blog.BlogDto;
import com.blogs.domain.dto.page.PageBlogDto;
import com.blogs.domain.vo.BlogVo;
import com.blogs.entity.Blog;
import com.blogs.mapper.BlogMapper;
import com.blogs.service.BlogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

  @Resource
  private BlogMapper blogMapper;

  // 查看所有博
  @Override
  public IPage<BlogVo> findAllPost(PageBlogDto pageBlogDto) {
    Page<Blog> page = new Page<>(pageBlogDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);
    if (pageBlogDto.getType().equals("最新")) {
      // 按照时间排序
      Page<Blog> postPage = blogMapper.selectPage(page, Wrappers.<Blog>lambdaQuery()
              .eq(Blog::getUserId, pageBlogDto.getUserId())
              .orderByDesc(Blog::getCreateTime)
              .eq(Blog::getStatus, 0)
      );
      return postPage.convert(blog -> CglibUtil.copy(blog, BlogVo.class));

    } else if (pageBlogDto.getType().equals("热门")) {
      // 按照点赞数排序
      Page<Blog> postPage = blogMapper.selectPage(page, Wrappers.<Blog>lambdaQuery()
              .eq(Blog::getUserId, pageBlogDto.getUserId())
              .eq(Blog::getStatus, 0)
              .orderByDesc(Blog::getSupport));

      return postPage.convert(blog -> CglibUtil.copy(blog, BlogVo.class));
    } else {
      throw new ServiceException("选择类型");
    }
  }

  // 根据title查询博客
  @Override
  public IPage<BlogVo> findByTitle(PageBlogDto pageBlogDto) {
    Page<Blog> page = new Page<>(pageBlogDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);

    Page<Blog> postPage = blogMapper.selectPage(page, Wrappers.<Blog>lambdaQuery()
            .eq(Blog::getUserId, pageBlogDto.getUserId())
            .like(Blog::getTitle, pageBlogDto.getTitle())
            .orderByDesc(Blog::getCreateTime));

    return postPage.convert(blog -> CglibUtil.copy(blog, BlogVo.class));
  }

  // 添加博客
  @Override
  public void insertBlog(BlogDto blogDto) {
    blogDto.setUserId(StpUtil.getLoginIdAsInt());
    blogMapper.insert(CglibUtil.copy(blogDto, Blog.class));
  }

  // 软删除博客
  @Override
  public void deleteBlog(Integer id) {
    // 软删除点击删除的时候将status改为1
    blogMapper.update(null, Wrappers.<Blog>lambdaUpdate()
            .eq(Blog::getId, id)
            .set(Blog::getStatus, 1)
            .eq(Blog::getUserId, StpUtil.getLoginIdAsInt()));
//    Blog blog = blogMapper.selectOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, id));
//    if(blog == null) throw new ServiceException("该博客不存在！");
//    blog.setStatus(1);
//    blogMapper.updateById(blog);

  }

  // 硬删除博客
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteBlogHard(List<Integer> blogIds) {
    // 先删除图片地址
    // 直接删除博客
    blogMapper.deleteBatchIds(blogIds);
  }

  // 浏览量增加
  @Override
  public void visit(Integer id) {
    // 获取当前blog
    Blog blog = blogMapper.selectOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, id));

    if (blog == null) throw new ServiceException("该博客不存在！");
    blog.setViewCount(blog.getViewCount() + 1);
    // 浏览量增加
    blogMapper.updateById(blog);
  }

  // 查找所有软删除博客

  @Override
  public IPage<BlogVo> findAllPostDel(PageBlogDto pageBlogDto) {
    Page<Blog> page = new Page<>(pageBlogDto.getPageNum(), GlobalConstants.PAGE_SIZE_DEFAULT);
    if (pageBlogDto.getType().equals("最新")) {
      // 按照时间排序
      Page<Blog> postPage = blogMapper.selectPage(page, Wrappers.<Blog>lambdaQuery()
              .eq(Blog::getUserId, pageBlogDto.getUserId())
              .orderByDesc(Blog::getCreateTime)
              .eq(Blog::getStatus, 1)
      );
      return postPage.convert(blog -> CglibUtil.copy(blog, BlogVo.class));

    } else if (pageBlogDto.getType().equals("热门")) {
      // 按照点赞数排序
      Page<Blog> postPage = blogMapper.selectPage(page, Wrappers.<Blog>lambdaQuery()
              .eq(Blog::getUserId, pageBlogDto.getUserId())
              .eq(Blog::getStatus, 1)
              .orderByDesc(Blog::getSupport));

      return postPage.convert(blog -> CglibUtil.copy(blog, BlogVo.class));
    } else {
      throw new ServiceException("选择类型");
    }
  }

  // 恢复博客
  @Override
  public void recoverBlog(Integer id) {
    // 软删除点击删除的时候将status改为1
    blogMapper.update(null, Wrappers.<Blog>lambdaUpdate()
            .eq(Blog::getId, id)
            .set(Blog::getStatus, 0)
            .eq(Blog::getUserId, StpUtil.getLoginIdAsInt()));
  }

  @Override
  public BlogVo findOne(Integer id) {
    Blog blog = blogMapper.selectOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, id));
    if (blog == null) throw new ServiceException("该博客不存在！");
    return CglibUtil.copy(blog, BlogVo.class);
  }

  @Override
  public void updateBlog(BlogDto blogDto) {
    blogMapper.updateBlog(CglibUtil.copy(blogDto, Blog.class));
  }

  @Override
  public void support(Integer id) {
    // 获取当前blog
    Blog blog = blogMapper.selectOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, id));

    if (blog == null) throw new ServiceException("该博客不存在！");
    blog.setSupport(blog.getSupport() + 1);
    // 浏览量增加
    blogMapper.updateById(blog);
  }

  @Override
  public void supportCancel(Integer id) {
    // 获取当前blog
    Blog blog = blogMapper.selectOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, id));

    if (blog == null) throw new ServiceException("该博客不存在！");
    blog.setSupport(blog.getSupport() - 1);
    // 浏览量增加
    blogMapper.updateById(blog);
  }

  @Override
  public void down(Integer id) {
    // 获取当前blog
    Blog blog = blogMapper.selectOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, id));

    if (blog == null) throw new ServiceException("该博客不存在！");
    blog.setDown(blog.getDown()+1);
    // 浏览量增加
    blogMapper.updateById(blog);
  }

  @Override
  public void downCancel(Integer id) {
    // 获取当前blog
    Blog blog = blogMapper.selectOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, id));

    if (blog == null) throw new ServiceException("该博客不存在！");
    blog.setDown(blog.getDown()-1);
    // 浏览量增加
    blogMapper.updateById(blog);
  }
}