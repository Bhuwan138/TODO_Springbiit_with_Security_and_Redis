package com.bhuwan.todo_with_auth_and_redis.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {
    private int status;
    private String message;
    private List<String> errors;

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
