package com.blogs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

  // 帖子id
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;

  // user用户自己的博客
  private Integer userId;

  // 博客标题
  private String title;

  // 博客的标签
  private String tag;

  // 博客的描述
  @TableField(value = "`desc`")
  private String desc;

  // 博客的内容
  private String details;

  // 浏览量
  private Integer viewCount;

  // 点赞
  private Integer support;

  // 点踩
  private Integer down;

  // 软删除
  private Integer status;

  // UpdateTime
  private Timestamp createTime;

  // UpdateTime
  private Timestamp updateTime;

}
