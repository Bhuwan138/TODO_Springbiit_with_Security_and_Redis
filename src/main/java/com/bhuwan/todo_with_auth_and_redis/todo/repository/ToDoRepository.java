package com.bhuwan.todo_with_auth_and_redis.todo.repository;

import com.bhuwan.todo_with_auth_and_redis.auth.model.User;
import com.bhuwan.todo_with_auth_and_redis.todo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findAllByUser(User user);
}