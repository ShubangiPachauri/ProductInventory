package com.demo.inventory.service;

import com.demo.inventory.model.Category;
import com.demo.inventory.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // CREATE
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // READ - All
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // READ - By ID
    public Category getCategoryById(Long id) {
        Optional<Category> optional = categoryRepository.findById(id);
        return optional.orElse(null);
    }

    // UPDATE
    public Category updateCategory(Long id, Category updatedCategory) {
        Category existing = getCategoryById(id);
        if (existing == null) return null;

        existing.setName(updatedCategory.getName());
        existing.setDescription(updatedCategory.getDescription());

        return categoryRepository.save(existing);
    }

    // DELETE
    public boolean deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) return false;
        categoryRepository.deleteById(id);
        return true;
    }
}
