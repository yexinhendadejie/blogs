package com.blogs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AliPay {
    @TableId(value = "id", type = IdType.AUTO)
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