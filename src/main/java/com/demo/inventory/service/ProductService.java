package com.demo.inventory.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.inventory.exception.DuplicateResourceException;
import com.demo.inventory.exception.ResourceNotFoundException;
import com.demo.inventory.csvhelper.CSVHelper;
import com.demo.inventory.model.Category;
import com.demo.inventory.model.Product;
import com.demo.inventory.model.Supplier;
import com.demo.inventory.repository.CategoryRepository;
import com.demo.inventory.repository.ProductRepository;
import com.demo.inventory.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final SupplierRepository supplierRepo;

    public Product create(Product product) {
        if (productRepo.existsBySku(product.getSku())) {
            throw new DuplicateResourceException("SKU already exists");
        }

        Category category = categoryRepo.findById(product.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Supplier supplier = supplierRepo.findById(product.getSupplier().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        product.setCategory(category);
        product.setSupplier(supplier);
        return productRepo.save(product);
    }

    public List<Product> getAll() {
        return productRepo.findAll();
    }

    public Product getById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Product update(Long id, Product p) {
        Product product = getById(id);
        product.setName(p.getName());
        product.setSku(p.getSku());
        product.setDescription(p.getDescription());
        product.setPrice(p.getPrice());
        product.setQuantityInStock(p.getQuantityInStock());
        product.setReorderLevel(p.getReorderLevel());
        product.setUnit(p.getUnit());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepo.save(product);
    }

    public void delete(Long id) {
        Product p = getById(id);
        productRepo.delete(p);
    }

    // ✅ CSV Save Method
    public void saveCSV(MultipartFile file) {
        try {
            List<Product> products = CSVHelper.csvToProducts(file.getInputStream());
            productRepo.saveAll(products);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store CSV data: " + e.getMessage());
        }
    }
}
