package com.blogs;

import com.blogs.common.netty.NettyServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class BlogsApplication implements CommandLineRunner {

    @Value("${netty.socket.port}")
    private Integer port;

    @Resource
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(BlogsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        this.nettyServer.start(this.port);
    }
}
