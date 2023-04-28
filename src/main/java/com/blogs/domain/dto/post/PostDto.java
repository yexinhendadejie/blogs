package com.blogs.domain.dto.post;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
public class PostDto {

  private Integer id;

  // user用户自己的博客
  private Integer userId;

  // 博客标题
  @NotBlank(message = "标题不能为空")
  private String title;


  // 博客的标签
  @NotBlank(message = "标签不能为空")
  private String tag;

  // 博客的描述
  @NotBlank(message = "描述不能为空")
  private String desc;


  // 博客的内容
  @NotBlank(message = "内容不能为空")
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
