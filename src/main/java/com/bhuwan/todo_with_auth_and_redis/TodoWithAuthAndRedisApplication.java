package com.bhuwan.todo_with_auth_and_redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TodoWithAuthAndRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoWithAuthAndRedisApplication.class, args);
    }

}
