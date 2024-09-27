package com.bhuwan.todo_with_auth_and_redis.auth.service;

import com.bhuwan.todo_with_auth_and_redis.auth.dto.LoginRequest;
import com.bhuwan.todo_with_auth_and_redis.auth.dto.LoginResponse;
import com.bhuwan.todo_with_auth_and_redis.auth.dto.RegisterRequest;
import com.bhuwan.todo_with_auth_and_redis.auth.model.Role;
import com.bhuwan.todo_with_auth_and_redis.auth.model.User;
import com.bhuwan.todo_with_auth_and_redis.auth.repository.RoleRepository;
import com.bhuwan.todo_with_auth_and_redis.auth.repository.UserRepository;
import com.bhuwan.todo_with_auth_and_redis.auth.security.JwtTokenProvider;
import com.bhuwan.todo_with_auth_and_redis.config.USER_ROLES;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.bhuwan.todo_with_auth_and_redis.config.USER_ROLES.ROLE_USER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleRepository roleRepository;

    @Override
    public void register(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Role userRole = roleRepository.findByName(ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        String token = jwtTokenProvider.generateToken(authentication);
        return new LoginResponse(token);
    }
}
