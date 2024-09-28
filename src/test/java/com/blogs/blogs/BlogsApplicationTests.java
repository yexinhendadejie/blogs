package com.blogs.blogs;

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

    interface b {
        void c();
    }

    public class d implements b {
        @Override
        public void c() {
            System.out.println("我执行了");
        }
    }



    @Test
    void a() {
        b b = new d();
        b.c();
    }


    public static void method() {
        System.out.println("方法引用");
        String a = null;
        System.out.println(a);
        a = "123";
        System.out.println(a);
    }


}
