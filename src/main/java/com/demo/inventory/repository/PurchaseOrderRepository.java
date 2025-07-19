package com.demo.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.inventory.model.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> 
{
	
}