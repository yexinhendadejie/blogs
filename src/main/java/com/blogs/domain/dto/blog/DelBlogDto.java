package com.blogs.domain.dto.blog;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class DelBlogDto {
  // 任务id
  @NotEmpty(message = "博客id不能为空")
  private List<Integer> blogIds;
}
