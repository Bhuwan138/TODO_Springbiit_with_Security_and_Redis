package com.bhuwan.todo_with_auth_and_redis.auth.model;

import com.bhuwan.todo_with_auth_and_redis.config.USER_ROLES;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private USER_ROLES name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}