package com.demo.inventory.service;

import com.demo.inventory.model.User;
import com.demo.inventory.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;

public String register(String username, String email, String password) {
    if (userRepository.findByUsername(username).isPresent()) {
        return "User already exists!";
    }

    User user = User.builder()
            .username(username)
            .email(email)
            .passwordHash(passwordEncoder.encode(password))
            .role("USER")
            .isActive(true)
            .createdAt(LocalDateTime.now())
            .build();

    userRepository.save(user);
    return "User registered successfully!";
}

public String login(String username, String password) {
    Optional<User> userOpt = userRepository.findByUsername(username);

    if (userOpt.isEmpty()) {
        return "User not found!";
    }

    User user = userOpt.get();
    if (passwordEncoder.matches(password, user.getPasswordHash())) {
        return "Login successful!";
    } else {
        return "Invalid password!";
    }
}
// Get all users method
public List<User> getAllUsers() {
 return userRepository.findAll();
}
}