package com.blogs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLikeDetail {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 点赞/点踩用户的id

    private Integer userId;

    // 被点赞/点踩用户的id
    private Integer targetId;

    // 点赞/点踩的帖子
    private Integer targetType;

    // 点赞还是点踩 1点赞 0点踩
    private Integer likeType;

    // 修改时间
    private Timestamp updateTime;

    // 创建时间
    private Timestamp createTime;

}
