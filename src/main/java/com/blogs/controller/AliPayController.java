package com.blogs.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.excel.EasyExcel;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blogs.common.config.AliPayConfig;
import com.blogs.common.validator.anno.LimitRequest;
import com.blogs.domain.dto.page.PageAliPayDto;
import com.blogs.domain.vo.aliPayVo.AliPayVo;
import com.blogs.entity.AliPay;
import com.blogs.mapper.AliPayMapper;
import com.blogs.service.AliPayService;
import com.blogs.utils.Resp;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// xjlugv6874@sandbox.com
// 9428521.24 - 30 = 9428491.24 + 30 = 9428521.24
@RestController
@RequestMapping("/alipay")
public class AliPayController {

    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";

    @Resource
    private AliPayConfig aliPayConfig;

    @Resource
    AliPayMapper aliPayMapper;

    @Resource
    AliPayService aliPayService;

    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay(AliPay aliPay, HttpServletResponse httpResponse) throws Exception {
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);

        // 2. 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.set("out_trade_no", aliPay.getTraceNo());  // 我们自己生成的订单编号
        bizContent.set("total_amount", aliPay.getTotalAmount()); // 订单的总金额
        bizContent.set("subject", aliPay.getSubject());   // 支付的名称
        bizContent.set("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
        request.setBizContent(bizContent.toString());

        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
            System.out.println("调用SDK生成表单失败");
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }
            String outTradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayConfig.getAlipayPublicKey(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                aliPayMapper.update(null, Wrappers.<AliPay>lambdaUpdate()
                        .set(AliPay::getAlipayTraceNo, alipayTradeNo)
                        .set(AliPay::getStatus, "1")
                        .eq(AliPay::getTraceNo, outTradeNo));
            }

            // 更新订单状态

        }
        return "success";
    }

    @GetMapping("/return")
    public Resp returnPay(AliPay aliPay) throws AlipayApiException {
//        // 7天无理由退款
//        String now = DateUtil.now();
//        Orders orders = ordersMapper.getByNo(aliPay.getTraceNo());
//        if (orders != null) {
//            // hutool工具类，判断时间间隔
//            long between = DateUtil.between(DateUtil.parseDateTime(orders.getPaymentTime()), DateUtil.parseDateTime(now), DateUnit.DAY);
//            if (between > 7) {
//                return Result.error("-1", "该订单已超过7天，不支持退款");
//            }
//        }


        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL,
                aliPayConfig.getAppId(), aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET,
                aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);
        // 2. 创建 Request，设置参数
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.set("trade_no", aliPay.getAlipayTraceNo());  // 支付宝回调的订单流水号
        bizContent.set("refund_amount", aliPay.getTotalAmount());  // 订单的总金额
        bizContent.set("out_request_no", aliPay.getTraceNo());   //  我的订单编号

        // 返回参数选项，按需传入
        //JSONArray queryOptions = new JSONArray();
        //queryOptions.add("refund_detail_item_list");
        //bizContent.put("query_options", queryOptions);

        request.setBizContent(bizContent.toString());

        // 3. 执行请求
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {  // 退款成功，isSuccess 为true
            System.out.println("调用成功");
            // 4. 更新数据库状态
            // 将status改为false
            aliPayMapper.update(null, Wrappers.<AliPay>lambdaUpdate()
                    .set(AliPay::getStatus, "2")
                    .eq(AliPay::getTraceNo, aliPay.getTraceNo()));
        } else {   // 退款失败，isSuccess 为false
            System.out.println(response.getBody());
            return Resp.error();
        }
        return Resp.ok();
    }

    // 根据商家订单查询支付宝订单号
    @GetMapping("/getAlipayTraceNo")
    public Resp getAlipayTraceNo(String traceNo) {
        AliPay aliPay = aliPayMapper.selectOne(Wrappers.<AliPay>lambdaQuery()
                .eq(AliPay::getTraceNo, traceNo));
        if (aliPay == null) {
            return Resp.error().msg("订单不存在");
        }
        return Resp.ok(aliPay.getAlipayTraceNo());
    }

    // 根据userId查询所有订单
    @PostMapping("/findAllPay")
    public Resp<IPage<AliPayVo>> findAllPay(@Validated @RequestBody PageAliPayDto pageAliPayDto) {
        return Resp.ok(aliPayService.findAllPay(pageAliPayDto));
    }

    // 根据条件订单号去查找分页
    @PostMapping("/findByTraceNo")
    public Resp<IPage<AliPayVo>> findByTraceNo(@Validated @RequestBody PageAliPayDto pageAliPayDto) {
        return Resp.ok(aliPayService.findByCondition(pageAliPayDto));
    }

    // 新增
    @PostMapping("/addAliPay")
    public Resp<Void> addAliPay(@Validated @RequestBody PageAliPayDto pageAliPayDto) {
        aliPayService.addAliPay(pageAliPayDto);
        return Resp.ok();
    }

    // 根据id删除
    @DeleteMapping("/deleteAliPay/{id}")
    public Resp<Void> deleteAliPay(@PathVariable Integer id) {
        aliPayService.deleteAliPay(id);
        return Resp.ok();
    }

    // 导出excel

    // 接口限制 1分钟只能按一次
    @LimitRequest(count = 1)
    @PostMapping("/exportExcel")
    public Resp<Void> download(HttpServletResponse response) {
        try {

            List<AliPay> aliPays = aliPayMapper.selectList(Wrappers.<AliPay>lambdaQuery()
                    .eq(AliPay::getUserId, StpUtil.getLoginIdAsInt()));
            // 循环打印alPays取出里面的status
            for (AliPay aliPay : aliPays) {
                if (aliPay.getStatus().equals("0")) {
                    aliPay.setStatus("未支付");
                } else if (aliPay.getStatus().equals("1")) {
                    aliPay.setStatus("已支付");
                } else if (aliPay.getStatus().equals("2")) {
                    aliPay.setStatus("已退款");
                }
            }
            // 设置文件导出的路径
            String separator = File.separator;
            String downloadsDirectory = System.getProperty("user.home") + separator + "Downloads" + separator;
            System.out.println(downloadsDirectory);
            String fileName = downloadsDirectory + "User" + System.currentTimeMillis() + ".xlsx";
            System.out.println(fileName);
//        File folder = new File(fileName);
//        if (!folder.isDirectory()) {
//            folder.mkdirs();
//        }
            // 这里需要指定写用哪个class去写，然后写到第一个sheet，名字为用户表，然后文件流会自动关闭
            EasyExcel.write(fileName, AliPay.class).sheet("支付信息表").doWrite(aliPays);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.ok();
    }
}