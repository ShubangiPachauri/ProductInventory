package com.demo.inventory.service;

import com.demo.inventory.model.Inventory;
import com.demo.inventory.model.Product;
import com.demo.inventory.repository.InventoryRepository;
import com.demo.inventory.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ProductRepository productRepo;
    private final InventoryRepository inventoryRepo;

    public List<Product> getAllProductsWithStock() {
        return productRepo.findAll();
    }

    public List<Product> getLowStockProducts() {
        return productRepo.findByQuantityInStockLessThanReorderLevel();
    }

    public List<Inventory> getInventoryHistory() {
        return inventoryRepo.findAll();
    }

    public String processCsvUpload(MultipartFile file) {
        if (file.isEmpty()) return "File is empty";
        if (!file.getOriginalFilename().endsWith(".csv")) return "Invalid file format";

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header
                }

                String[] fields = line.split(",");
                Long productId = Long.parseLong(fields[0].trim());
                int quantityToAdd = Integer.parseInt(fields[1].trim());

                Product product = productRepo.findById(productId).orElseThrow();
                product.setQuantityInStock(product.getQuantityInStock() + quantityToAdd);
                productRepo.save(product);
            }

            return "CSV uploaded and stock updated successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
