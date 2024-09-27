package com.bhuwan.todo_with_auth_and_redis.auth.validator;

import com.bhuwan.todo_with_auth_and_redis.auth.dto.LoginRequest;
import com.bhuwan.todo_with_auth_and_redis.auth.dto.RegisterRequest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthValidator {
    public List<String> validateRegisterRequest(RegisterRequest registerRequest) {
        List<String> errors = new ArrayList<>();

        if (!StringUtils.hasText(registerRequest.getUsername())) {
            errors.add("Username is mandatory.");
        }

        if (!StringUtils.hasText(registerRequest.getPassword())) {
            errors.add("Password is mandatory.");
        } else if (registerRequest.getPassword().length() < 6) {
            errors.add("Password must be at least 6 characters long.");
        }

        return errors;
    }

    public List<String> validateLoginRequest(LoginRequest loginRequest) {
        List<String> errors = new ArrayList<>();

        if (!StringUtils.hasText(loginRequest.getUsername())) {
            errors.add("Username is mandatory.");
        }

        if (!StringUtils.hasText(loginRequest.getPassword())) {
            errors.add("Password is mandatory.");
        }

        return errors;
    }
}
