package com.blogs.domain.dto.page;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PageAliPayDto extends PageDto {
    private Integer id;
    private Integer userId;
    private String traceNo;
    private String totalAmount;
    private String subject;
    private String alipayTraceNo;
    private String status;
    private Timestamp createTime;
    private Timestamp updateTime;
}
