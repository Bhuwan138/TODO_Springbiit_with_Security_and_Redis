package com.bhuwan.todo_with_auth_and_redis.auth.controller;

import com.bhuwan.todo_with_auth_and_redis.ApiResponse;
import com.bhuwan.todo_with_auth_and_redis.auth.dto.LoginRequest;
import com.bhuwan.todo_with_auth_and_redis.auth.dto.LoginResponse;
import com.bhuwan.todo_with_auth_and_redis.auth.dto.RegisterRequest;
import com.bhuwan.todo_with_auth_and_redis.auth.service.AuthService;
import com.bhuwan.todo_with_auth_and_redis.auth.validator.AuthValidator;
import com.bhuwan.todo_with_auth_and_redis.exception.ApiError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthValidator authValidator;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest){

        // validator
        List<String> validationErrors = authValidator.validateRegisterRequest(registerRequest);
        if (!validationErrors.isEmpty()) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation Error", validationErrors);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid data", apiError), HttpStatus.BAD_REQUEST);
        }
        // Register the user
        authService.register(registerRequest);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED.value(), "User registered successfully", null), HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Validate login request
        List<String> validationErrors = authValidator.validateLoginRequest(loginRequest);
        if (!validationErrors.isEmpty()) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation Error", validationErrors);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid data", apiError), HttpStatus.BAD_REQUEST);
        }

        // Authenticate and generate JWT token
        LoginResponse loginResponse = authService.login(loginRequest);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Login successful", loginResponse), HttpStatus.OK);
    }

}
