package com.demo.inventory.controller;

import com.demo.inventory.model.Category;
import com.demo.inventory.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepo;

    public CategoryController(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return new ResponseEntity<>(categoryRepo.save(category), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }
}
