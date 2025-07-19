package com.demo.inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.demo.inventory.csvhelper.CSVHelper;
import com.demo.inventory.model.Product;
import com.demo.inventory.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product p) {
        return new ResponseEntity<>(productService.create(p), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody Product p) {
        return productService.update(id, p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //CSV Upload 
    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (!CSVHelper.isCSVFormat(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a CSV file!");
        }

        try {
            productService.saveCSV(file);
            return ResponseEntity.ok("CSV file uploaded and data saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }
}
