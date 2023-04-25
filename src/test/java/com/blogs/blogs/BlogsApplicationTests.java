package com.blogs.blogs;

import cn.hutool.crypto.SecureUtil;
import com.blogs.entity.User;
import com.blogs.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class BlogsApplicationTests {

    @Autowired
    UserMapper userMapper;
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
        User user  = new User();
        user.setId(9);
        user.setLabel("哈哈");

        userMapper.updateById(user);

    }


}
