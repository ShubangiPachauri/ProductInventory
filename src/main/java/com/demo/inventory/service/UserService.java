package com.demo.inventory.service;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.demo.inventory.exception.DuplicateResourceException;
import com.demo.inventory.exception.ResourceNotFoundException;
import com.demo.inventory.model.User;
import com.demo.inventory.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public User create(@Valid User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already taken");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }
        return userRepo.save(user);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User update(Long id, @Valid User request) {
        User user = getById(id);
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPasswordHash());
        user.setRole(request.getRole());
        return userRepo.save(user);
    }

    public void delete(Long id) {
        User user = getById(id);
        userRepo.delete(user);
    }
}
