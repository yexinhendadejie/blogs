package com.blogs.domain.vo.aliPayVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliPayVo {
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
