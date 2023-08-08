package com.blogs.domain.dto.page;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class PageBlogDto extends PageDto {

  // user用户自己的博客
  private Integer userId;

  // 博客标题
  private String title;

  // 博客的标签
  private List<String> tag;

  // 博客的内容
  private String details;

  // UpdateTime
  private Timestamp createTime;

  // UpdateTime
  private Timestamp updateTime;

  // 博客热门/最新分类
  private String type;
}