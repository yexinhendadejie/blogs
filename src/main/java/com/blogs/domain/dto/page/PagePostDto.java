package com.blogs.domain.dto.page;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class PagePostDto extends PageDto {

  // 帖子id
  private Integer id;

  // user用户自己的博客
  private Integer userId;

  // 博客标题

  private String title;

  // 博客的标签
  private String tag;

  // 博客的内容
  private String details;

  // 收藏
  private Integer collectionCount;

  // 点赞

  private Integer support;

  // 点踩
  private Integer down;

  // UpdateTime
  private Timestamp createTime;

  // UpdateTime
  private Timestamp updateTime;
}
