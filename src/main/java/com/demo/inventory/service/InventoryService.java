package com.demo.inventory.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.demo.inventory.exception.ResourceNotFoundException;
import com.demo.inventory.model.Inventory;
import com.demo.inventory.model.Product;
import com.demo.inventory.repository.InventoryRepository;
import com.demo.inventory.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepo;
    private final ProductRepository productRepo;

    @Transactional
    public Inventory adjustStock(Long productId, Inventory request) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (request.getAdjustedQuantity() == 0)
            throw new IllegalArgumentException("Adjusted quantity cannot be zero");

        int currentStock = product.getQuantityInStock();
        int newStock = currentStock + request.getAdjustedQuantity();

        if (newStock < 0)
            throw new IllegalArgumentException("Insufficient stock. Cannot reduce more than available.");

        product.setQuantityInStock(newStock);
        product.setUpdatedAt(LocalDateTime.now());
        productRepo.save(product);

        Inventory inv = Inventory.builder()
                .product(product)
                .stockIn(request.getAdjustedQuantity() > 0 ? request.getAdjustedQuantity() : 0)
                .stockOut(request.getAdjustedQuantity() < 0 ? Math.abs(request.getAdjustedQuantity()) : 0)
                .adjustedQuantity(request.getAdjustedQuantity())
                .adjustmentReason(request.getAdjustmentReason())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return inventoryRepo.save(inv);
    }

    public List<Inventory> getAll() {
        return inventoryRepo.findAll();
    }
}
