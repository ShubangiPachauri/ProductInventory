package com.demo.inventory.controller;

import com.demo.inventory.model.Supplier;
import com.demo.inventory.repository.SupplierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierRepository supplierRepo;

    public SupplierController(SupplierRepository supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    @PostMapping
    public ResponseEntity<Supplier> create(@RequestBody Supplier supplier) {
        return new ResponseEntity<>(supplierRepo.save(supplier), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Supplier> getAll() {
        return supplierRepo.findAll();
    }
}
