package com.bhuwan.todo_with_auth_and_redis.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateToDoRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @Size(max = 250, message = "Description must not exceed 250 characters")
    private String description;

    private Boolean completed = false;
}
