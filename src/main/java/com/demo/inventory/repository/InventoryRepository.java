package com.demo.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.inventory.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
