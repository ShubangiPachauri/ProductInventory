package com.demo.inventory.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.inventory.exception.ResourceNotFoundException;
import com.demo.inventory.model.OrderStatus;
import com.demo.inventory.model.Product;
import com.demo.inventory.model.PurchaseOrder;
import com.demo.inventory.model.PurchaseOrderItem;
import com.demo.inventory.model.Supplier;
import com.demo.inventory.repository.ProductRepository;
import com.demo.inventory.repository.PurchaseOrderRepository;
import com.demo.inventory.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository orderRepo;
    private final SupplierRepository supplierRepo;
    private final ProductRepository productRepo;

    public PurchaseOrder createOrder(PurchaseOrder orderRequest) {
        Supplier supplier = supplierRepo.findById(orderRequest.getSupplier().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        orderRequest.setSupplier(supplier);
        orderRequest.setCreatedAt(LocalDateTime.now());
        orderRequest.setStatus(OrderStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;
        for (PurchaseOrderItem item : orderRequest.getItems()) {
            Product product = productRepo.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            item.setProduct(product);
            item.setPurchaseOrder(orderRequest);

            BigDecimal itemTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getOrderedQuantity()));
            total = total.add(itemTotal);
        }

        orderRequest.setTotalAmount(total);
        return orderRepo.save(orderRequest);
    }

    public List<PurchaseOrder> getAll() {
        return orderRepo.findAll();
    }

    public PurchaseOrder getById(Long id) {
        return orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public void delete(Long id) {
        PurchaseOrder po = getById(id);
        orderRepo.delete(po);
    }
}
