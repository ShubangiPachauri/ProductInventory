package com.demo.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.inventory.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> 
{
    boolean existsByName(String name);
}