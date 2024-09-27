package com.bhuwan.todo_with_auth_and_redis.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
