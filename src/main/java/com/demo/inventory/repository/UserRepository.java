package com.demo.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.inventory.model.User;

public interface UserRepository extends JpaRepository<User, Long>
{
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}