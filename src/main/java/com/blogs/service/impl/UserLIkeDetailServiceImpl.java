package com.blogs.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blogs.domain.vo.LikeTypeVo;
import com.blogs.entity.Collection;
import com.blogs.entity.UserLikeDetail;
import com.blogs.mapper.CollectionMapper;
import com.blogs.mapper.UserLikeDetailMapper;
import com.blogs.service.UserLIkeDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserLIkeDetailServiceImpl implements UserLIkeDetailService {

    @Resource
    UserLikeDetailMapper userLikeDetailMapper;

    @Resource
    CollectionMapper collectionMapper;

    @Override
    public LikeTypeVo likeType(Integer userId, Integer targetId) {
        UserLikeDetail userLikeDetail = userLikeDetailMapper.selectOne(Wrappers.<UserLikeDetail>lambdaQuery()
                .eq(UserLikeDetail::getUserId, userId)
                .eq(UserLikeDetail::getTargetId, targetId));

        Collection collection = collectionMapper.selectOne(Wrappers.<Collection>lambdaQuery()
                .eq(Collection::getUserId, userId)
                .eq(Collection::getTargetId, targetId));

        // 将是否收藏存入vo
        LikeTypeVo likeTypeVo = new LikeTypeVo();
        likeTypeVo.setIsCollection(collection != null ? collection.getIsCollection() : false);

        likeTypeVo.setIsDisLike(userLikeDetail != null && userLikeDetail.getLikeType() == 0);
        likeTypeVo.setIsLike(userLikeDetail != null && userLikeDetail.getLikeType() != 0);
        return likeTypeVo;
    }
}
