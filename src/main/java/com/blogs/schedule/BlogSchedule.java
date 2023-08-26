package com.blogs.schedule;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blogs.common.enums.ScheduleEnum;
import com.blogs.entity.Blog;
import com.blogs.mapper.BlogMapper;
import com.blogs.service.BlogService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlogSchedule {

  @Resource
  BlogMapper blogMapper;

  @Resource
  BlogService blogService;

  // 每天凌晨1点半执行
  @Scheduled(cron = "0 30 1 * * ?")
  public void hardDelTask() {
    // 获取七天前时间
    Long overdueDays = System.currentTimeMillis() - ScheduleEnum.BLOG.getOverdueTime();
    // 查询过期七天的博客中间表id
    List<Integer> expiredBlogId = blogMapper.selectList(Wrappers.<com.blogs.entity.Blog>lambdaQuery()
                    .ge(Blog::getCreateTime, overdueDays))
            .stream()
            .map(Blog::getId)
            .collect(Collectors.toList());
    // 调用 service 执行硬删除操作
    blogService.deleteBlogHard(expiredBlogId);
  }
}