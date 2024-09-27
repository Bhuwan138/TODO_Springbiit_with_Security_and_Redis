package com.bhuwan.todo_with_auth_and_redis.todo.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
}
