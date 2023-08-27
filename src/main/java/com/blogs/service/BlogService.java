package com.blogs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.blog.BlogDto;
import com.blogs.domain.dto.page.PageBlogDto;
import com.blogs.domain.vo.BlogVo;

import java.util.List;

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
    void deleteBlogHard(List<Integer> blogIds);

    // 浏览量增加
    void visit(Integer id);

    // 查找所有软删除博客
    IPage<BlogVo> findAllPostDel(PageBlogDto pageBlogDto);

    // 恢复软删除博客
    void recoverBlog(Integer id);

    // 查找单个博客
    BlogVo findOne(Integer id);

    // 修改博客
    void updateBlog(BlogDto blogDto);

    // 点赞增加
    void support(Integer id);

    // 已经点赞但是取消了
    void supportCancel(Integer id);

    // 踩
    void down(Integer id);

    // 已经踩但是取消了
    void downCancel(Integer id);

}