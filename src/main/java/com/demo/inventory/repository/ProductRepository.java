package com.demo.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.inventory.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{
	  boolean existsBySku(String sku);
	  @Query(value = "SELECT * FROM product WHERE quantity_in_stock < reorder_level", nativeQuery = true)
	  List<Product> findByQuantityInStockLessThanReorderLevel();
	}