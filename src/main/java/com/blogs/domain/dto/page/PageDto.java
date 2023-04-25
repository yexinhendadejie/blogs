package com.blogs.domain.dto.page;

import lombok.Data;

@Data
public class PageDto {
  protected Integer pageNum = 1;
  protected String search = "";
}
