package com.blogs.domain.dto.page;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PageCollectionDto extends PageDto {

    /**
     * 主键id
     */
    private Integer id;


    // 收藏的帖子id
    private Integer blogId;

    // 当前用户的id
    private Integer userId;

    // 收藏表的用户
    private Integer collectionUserId;

    // 0->不亮图标 1->亮图标
    private Integer status;

    // UpdateTime
    private Timestamp updateTime;

    // UpdateTime
    private Timestamp createTime;

}
