package com.demo.inventory.controller;

import com.demo.inventory.model.Inventory;
import com.demo.inventory.model.Product;
import com.demo.inventory.service.ReportService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/stock-levels")
    public ResponseEntity<List<Product>> getStockLevels() {
        return ResponseEntity.ok(reportService.getAllProductsWithStock());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStockAlerts() {
        return ResponseEntity.ok(reportService.getLowStockProducts());
    }

    @GetMapping("/inventory-history")
    public ResponseEntity<List<Inventory>> getInventoryHistory() {
        return ResponseEntity.ok(reportService.getInventoryHistory());
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        String message = reportService.processCsvUpload(file);
        return ResponseEntity.ok(message);
    }
}
