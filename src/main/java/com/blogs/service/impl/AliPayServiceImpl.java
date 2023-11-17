package com.blogs.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blogs.domain.dto.page.PageAliPayDto;
import com.blogs.domain.vo.aliPayVo.AliPayVo;
import com.blogs.entity.AliPay;
import com.blogs.mapper.AliPayMapper;
import com.blogs.service.AliPayService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AliPayServiceImpl implements AliPayService {

    @Resource
    AliPayMapper aliPayMapper;

    @Override
    public IPage<AliPayVo> findAllPay(PageAliPayDto pageAliPayDto) {
        // 根据userId进行分页 每页10个
        Page<AliPay> page = new Page<>(pageAliPayDto.getPageNum(), 10);
        IPage<AliPay> aliPayIPage = aliPayMapper.selectPage(page, Wrappers.<AliPay>lambdaQuery()
                .eq(AliPay::getUserId, StpUtil.getLoginIdAsInt())
                .orderByDesc(AliPay::getCreateTime)
        );
        return aliPayIPage.convert(aliPay -> CglibUtil.copy(aliPay, AliPayVo.class));
    }

    @Override
    public IPage<AliPayVo> findByCondition(PageAliPayDto pageAliPayDto) {
        // 根据userId进行分页 每页10个
        Page<AliPay> page = new Page<>(pageAliPayDto.getPageNum(), 10);
        IPage<AliPay> aliPayIPage = aliPayMapper.selectPage(page, Wrappers.<AliPay>lambdaQuery()
                .eq(AliPay::getUserId, StpUtil.getLoginIdAsInt())
                .like(pageAliPayDto.getTraceNo() != null, AliPay::getTraceNo, pageAliPayDto.getTraceNo())
                .eq(pageAliPayDto.getStatus() != null, AliPay::getStatus, pageAliPayDto.getStatus())
                .orderByDesc(AliPay::getCreateTime)
        );
        return aliPayIPage.convert(aliPay -> CglibUtil.copy(aliPay, AliPayVo.class));
    }

    @Override
    public void addAliPay(PageAliPayDto pageAliPayDto) {
        AliPay aliPay = new AliPay();
        aliPay.setUserId(StpUtil.getLoginIdAsInt());
        aliPay.setTraceNo(pageAliPayDto.getTraceNo());
        aliPay.setSubject(pageAliPayDto.getSubject());
        aliPay.setTotalAmount(pageAliPayDto.getTotalAmount());
        aliPay.setStatus("0");
        aliPayMapper.insert(aliPay);
    }

    @Override
    public void deleteAliPay(Integer id) {
        aliPayMapper.deleteById(id);
    }

    @Override
    public List<AliPayVo> getAliPayList() {
        List<AliPay> aliPaysList = aliPayMapper.selectList(Wrappers.<AliPay>lambdaQuery()
                .eq(AliPay::getUserId, StpUtil.getLoginIdAsInt()));
        // Map AliPay entities to AliPayVO
        List<AliPayVo> aliPayVOList = aliPaysList.stream()
                .map(aliPay -> {
                    AliPayVo aliPayVo = new AliPayVo();
                    BeanUtils.copyProperties(aliPay, aliPayVo);
                    return aliPayVo;
                })
                .collect(Collectors.toList());

        return aliPayVOList;
    }
}
