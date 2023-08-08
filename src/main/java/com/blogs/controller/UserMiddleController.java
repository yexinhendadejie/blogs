package com.blogs.controller;

import com.blogs.domain.dto.media.UserMediaDto;
import com.blogs.domain.dto.user.RegisterForEmailDto;
import com.blogs.service.UserMiddleService;
import com.blogs.utils.Resp;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userMiddle")
public class UserMiddleController {
  @Resource
  private UserMiddleService userMiddleService;

  @PutMapping ("/sumUserMiddle")
  public Resp<Integer> sumUserMiddle(@RequestBody @Validated UserMediaDto userMediaDto) {
    Integer sum = userMiddleService.sumUserMiddle(userMediaDto);
    return Resp.ok(sum);
  }
}
