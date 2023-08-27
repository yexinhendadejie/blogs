package com.blogs.controller;

import com.blogs.domain.vo.LikeTypeVo;
import com.blogs.service.UserLIkeDetailService;
import com.blogs.utils.Resp;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/likeType")
public class LikeTypeController {

    @Resource
    UserLIkeDetailService userLIkeDetailService;

    @PostMapping("/blogStatus/{userId}/{targetId}")
    public Resp<LikeTypeVo> blogStatus(@PathVariable Integer userId, @PathVariable Integer targetId) {
        return Resp.ok(userLIkeDetailService.likeType(userId, targetId));
    }
}
