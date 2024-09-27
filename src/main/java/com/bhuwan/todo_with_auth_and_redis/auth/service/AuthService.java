package com.bhuwan.todo_with_auth_and_redis.auth.service;

import com.bhuwan.todo_with_auth_and_redis.auth.dto.LoginRequest;
import com.bhuwan.todo_with_auth_and_redis.auth.dto.LoginResponse;
import com.bhuwan.todo_with_auth_and_redis.auth.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
}
