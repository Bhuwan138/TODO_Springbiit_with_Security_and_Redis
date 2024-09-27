package com.bhuwan.todo_with_auth_and_redis.todo.service;

import com.bhuwan.todo_with_auth_and_redis.auth.model.User;
import com.bhuwan.todo_with_auth_and_redis.auth.repository.UserRepository;
import com.bhuwan.todo_with_auth_and_redis.todo.dto.CreateToDoRequest;
import com.bhuwan.todo_with_auth_and_redis.todo.dto.UpdateToDoRequest;
import com.bhuwan.todo_with_auth_and_redis.todo.model.ToDo;
import com.bhuwan.todo_with_auth_and_redis.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;

    private User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @CacheEvict(value = "todos", allEntries = true)
    public ToDo createToDo(CreateToDoRequest createToDoRequest) {
        User currentUser = getCurrentAuthenticatedUser();
        ToDo toDo = new ToDo();
        toDo.setTitle(createToDoRequest.getTitle());
        toDo.setDescription(createToDoRequest.getDescription());
        toDo.setCompleted(createToDoRequest.getCompleted());
        toDo.setCreatedAt(java.time.LocalDateTime.now());
        toDo.setUser(currentUser);
        return toDoRepository.save(toDo);
    }

    @Override
    @CachePut(cacheNames = "todos", key = "#id")
    public ToDo updateToDo(Long id, UpdateToDoRequest updateToDoRequest) {
        ToDo toDo = new ToDo();
        toDo.setTitle(updateToDoRequest.getTitle());
        toDo.setDescription(updateToDoRequest.getDescription());
        toDo.setCompleted(updateToDoRequest.getCompleted());

        ToDo existingToDo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("To-Do not found"));

        User currentUser = getCurrentAuthenticatedUser();

        // Ensure the authenticated user is the owner of the To-Do
        if (!existingToDo.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized to update this To-Do.");
        }


        existingToDo.setTitle(toDo.getTitle());
        existingToDo.setDescription(toDo.getDescription());
        existingToDo.setCompleted(toDo.getCompleted());
        existingToDo.setUpdatedAt(java.time.LocalDateTime.now());

        return toDoRepository.save(existingToDo);
    }

    @Override
    @CacheEvict(cacheNames = "todos", key = "#id", beforeInvocation = true)
    public void deleteToDo(Long id) {
        ToDo existingToDo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("To-Do not found"));

        User currentUser = getCurrentAuthenticatedUser();

        // Ensure the authenticated user is the owner of the To-Do
        if (!existingToDo.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized to delete this To-Do.");
        }

        toDoRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "todos", key = "#id")
    public ToDo getToDoById(Long id) {
        ToDo toDo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("To-Do not found"));

        User currentUser = getCurrentAuthenticatedUser();

        // Ensure the authenticated user is the owner of the To-Do
        if (!toDo.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized to view this To-Do.");
        }

        return toDo;
    }

    @Override
    @Cacheable(value = "todos", key = "#root.methodName")
    public List<ToDo> getAllToDos() {
        User currentUser = getCurrentAuthenticatedUser();
        return toDoRepository.findAllByUser(currentUser);
    }
}
