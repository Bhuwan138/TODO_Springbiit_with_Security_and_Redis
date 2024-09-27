package com.bhuwan.todo_with_auth_and_redis.todo.controller;

import com.bhuwan.todo_with_auth_and_redis.ApiResponse;
import com.bhuwan.todo_with_auth_and_redis.todo.dto.CreateToDoRequest;
import com.bhuwan.todo_with_auth_and_redis.todo.dto.ToDoResponse;
import com.bhuwan.todo_with_auth_and_redis.todo.dto.UpdateToDoRequest;
import com.bhuwan.todo_with_auth_and_redis.todo.dto.UserResponse;
import com.bhuwan.todo_with_auth_and_redis.todo.model.ToDo;
import com.bhuwan.todo_with_auth_and_redis.todo.service.ToDoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/todos")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    private ToDoResponse toResponse(ToDo toDo) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(toDo.getUser().getId());
        userResponse.setUsername(toDo.getUser().getUsername());

        ToDoResponse response = new ToDoResponse();
        response.setId(toDo.getId());
        response.setTitle(toDo.getTitle());
        response.setDescription(toDo.getDescription());
        response.setCompleted(toDo.getCompleted());
        response.setCreatedAt(toDo.getCreatedAt());
        response.setUpdatedAt(toDo.getUpdatedAt());
        response.setUser(userResponse);

        return response;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ToDoResponse>> createToDo(
            @Valid @RequestBody CreateToDoRequest createToDoRequest) {

        ToDo createdToDo = toDoService.createToDo(createToDoRequest);
        ToDoResponse response = toResponse(createdToDo);

        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.CREATED.value(), "To-Do created successfully", response),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ToDoResponse>> getToDoById(@PathVariable Long id) {
        ToDo toDo = toDoService.getToDoById(id);
        ToDoResponse response = toResponse(toDo);

        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), "To-Do retrieved successfully", response),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ToDoResponse>>> getAllToDos() {
        List<ToDo> toDos = toDoService.getAllToDos();
        List<ToDoResponse> responses = toDos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), "All To-Dos retrieved successfully", responses),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ToDoResponse>> updateToDo(
            @PathVariable Long id,
            @Valid @RequestBody UpdateToDoRequest updateToDoRequest) {



        ToDo updatedToDo = toDoService.updateToDo(id, updateToDoRequest);
        ToDoResponse response = toResponse(updatedToDo);

        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), "To-Do updated successfully", response),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteToDo(@PathVariable Long id) {
        toDoService.deleteToDo(id);

        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), "To-Do deleted successfully", null),
                HttpStatus.OK);
    }
}
