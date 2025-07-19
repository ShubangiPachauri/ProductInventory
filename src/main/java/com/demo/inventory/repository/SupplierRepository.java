package com.demo.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.inventory.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long>
{
	  boolean existsByEmail(String email);
	}