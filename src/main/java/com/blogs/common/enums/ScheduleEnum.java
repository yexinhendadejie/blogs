package com.blogs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ScheduleEnum {

    // 博客七天过期
    BLOG(86400000L * 7),

    // 收藏博客七天过期
    COLLECTION_BLOG(86400000L * 7);

    // 过期天数(时间戳)
    private Long overdueTime;
}
