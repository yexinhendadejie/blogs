package com.blogs.blogs;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blogs.entity.AliPay;
import com.blogs.entity.User;
import com.blogs.mapper.AliPayMapper;
import com.blogs.mapper.BlogMapper;
import com.blogs.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class BlogsApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Resource
    private BlogMapper postMapper;

    @Resource
    private AliPayMapper aliPayMapper;
    @Test
    void contextLoads() {

        User user1 = new User();
        user1.setPwd("123");
        user1.setId(1);

        User user2 = new User();
        user2.setPwd("12356");
        user2.setId(2);

        List<Integer> numbers = Arrays.asList(user1,user2).stream().map(User::getId).collect(Collectors.toList());
        System.out.println(numbers);
    }

    @Test
    void setUSerInfo(){
        // 查询所有
        List<AliPay> aliPays = aliPayMapper.selectList(Wrappers.<AliPay>lambdaQuery()
                .eq(AliPay::getUserId, 26));
        System.out.println(aliPays);

    }


}
