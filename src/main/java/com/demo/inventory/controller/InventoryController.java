package com.demo.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.demo.inventory.model.Inventory;
import com.demo.inventory.service.InventoryService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/{productId}")
    public ResponseEntity<Inventory> adjustStock(
            @PathVariable Long productId,
            @Valid @RequestBody Inventory inventory) {
        return new ResponseEntity<>(inventoryService.adjustStock(productId, inventory), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Inventory> getAll() {
        return inventoryService.getAll();
    }
}
