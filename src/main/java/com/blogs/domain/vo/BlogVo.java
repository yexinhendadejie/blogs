package com.blogs.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogVo {

  // 帖子id
  private Integer id;

  // user用户自己的博客
  private Integer uuid;

  // 博客标题
  private String title;

  // 博客的标签
  private String tag;

  // 博客的内容
  private String details;

  // 收藏
  private Integer collectionCount;

  // 浏览量
  private Integer viewCount;

  // 点赞

  private Integer support;

  // 点踩
  private Integer down;

  // UpdateTime
  private Timestamp createTime;

  // UpdateTime
  private Timestamp updateTime;
}
