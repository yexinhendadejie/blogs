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
public class Collection {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 收藏的帖子id
    private Integer targetId;

    // 当前用户的id
    private Integer userId;


    // 是否收藏 0->不收藏 1->收藏
    private Boolean isCollection;

    @TableField(exist = false)
    // 博客信息
    private Blog blog;

    // UpdateTime
    private Timestamp updateTime;

    // UpdateTime
    private Timestamp createTime;


}
