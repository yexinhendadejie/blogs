package com.blogs.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeTypeVo {

    // 是点赞
    private Boolean isLike;

    // 是点踩
    private Boolean isDisLike;

    // 是否收藏
    private Boolean isCollection;
}
