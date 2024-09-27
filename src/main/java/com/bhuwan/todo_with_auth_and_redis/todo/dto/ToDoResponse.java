package com.bhuwan.todo_with_auth_and_redis.todo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ToDoResponse {
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponse user;
}
