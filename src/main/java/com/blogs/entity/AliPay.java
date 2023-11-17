package com.blogs.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
@ExcelIgnoreUnannotated //忽视无注解的属性
@ContentRowHeight(20) //文本高度
@HeadRowHeight(20) //标题高度
public class AliPay {
    @TableId(value = "id", type = IdType.AUTO)
    @ColumnWidth(20) //列宽
    @ExcelProperty(value = {"支付信息", "编号"}, index = 0) //value：第一个值是标题,第二个是字段行名称,index表示哪列位置
    private Integer id;
    @ExcelIgnore
    private Integer userId;
    @ColumnWidth(20) //列宽
    @ExcelProperty(value = {"支付信息", "订单号"}, index = 1) //value：第一个值是标题,第二个是字段行名称,index表示哪列位置
    private String traceNo;
    @ColumnWidth(20) //列宽
    @ExcelProperty(value = {"支付信息", "订单金额"}, index = 2) //value：第一个值是标题,第二个是字段行名称,index表示哪列位置
    private String totalAmount;
    @ColumnWidth(20) //列宽
    @ExcelProperty(value = {"支付信息", "支付名称"}, index = 3) //value：第一个值是标题,第二个是字段行名称,index表示哪列位置
    private String subject;
    @ColumnWidth(20) //列宽
    @ExcelProperty(value = {"支付信息", "支付状态"}, index = 4) //value：第一个值是标题,第二个是字段行名称,index表示哪列位置
    private String status;
    @ColumnWidth(20) //列宽
    @ExcelProperty(value = {"支付信息", "交易凭证号"}, index = 5) //value：第一个值是标题,第二个是字段行名称,index表示哪列位置
    private String alipayTraceNo;
    @ExcelIgnore
    private Timestamp createTime;
    @ExcelIgnore
    private Timestamp updateTime;
}