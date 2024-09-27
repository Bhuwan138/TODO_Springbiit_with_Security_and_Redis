package com.bhuwan.todo_with_auth_and_redis.todo.service;

import com.bhuwan.todo_with_auth_and_redis.todo.dto.CreateToDoRequest;
import com.bhuwan.todo_with_auth_and_redis.todo.dto.UpdateToDoRequest;
import com.bhuwan.todo_with_auth_and_redis.todo.model.ToDo;

import java.util.List;

public interface ToDoService {
    ToDo createToDo(CreateToDoRequest createToDoRequest);

    ToDo getToDoById(Long id);

    List<ToDo> getAllToDos();

    ToDo updateToDo(Long id, UpdateToDoRequest updateToDoRequest);

    void deleteToDo(Long id);
}
