package com.blogs.domain.vo.collection;

import com.blogs.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionVo {

    private Integer id;

    // 收藏的帖子id
    private Integer targetId;

    // 当前用户的id
    private Integer userId;

    // 0->不亮图标 1->亮图标
    private Boolean isCollection;

    // 博客信息
    private Blog blog;

    // UpdateTime
    private Timestamp updateTime;

    // UpdateTime
    private Timestamp createTime;

}
