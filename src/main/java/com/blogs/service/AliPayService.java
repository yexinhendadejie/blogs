package com.blogs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blogs.domain.dto.page.PageAliPayDto;
import com.blogs.domain.vo.aliPayVo.AliPayVo;

public interface AliPayService {

    // 根据id查找所有支付信息，分页查找
    IPage<AliPayVo> findAllPay(PageAliPayDto pageAliPayDto);

    // 根据条件订单号去查找分页
    IPage<AliPayVo> findByCondition(PageAliPayDto pageAliPayDto);


    // 新增一条支付信息
    void addAliPay(PageAliPayDto pageAliPayDto);

    // 根据id删除一条支付信息
    void deleteAliPay(Integer id);
}
