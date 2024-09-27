package com.bhuwan.todo_with_auth_and_redis.auth.repository;

import com.bhuwan.todo_with_auth_and_redis.auth.model.Role;
import com.bhuwan.todo_with_auth_and_redis.config.USER_ROLES;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(USER_ROLES name);
}
