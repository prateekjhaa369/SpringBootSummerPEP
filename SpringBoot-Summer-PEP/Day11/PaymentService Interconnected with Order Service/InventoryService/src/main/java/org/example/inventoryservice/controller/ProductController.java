package org.example.inventoryservice.controller;

import org.example.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class ProductController {

    @Autowired
    private InventoryService service;

    @GetMapping("/check/{productName}")
    public String checkProductAvailability(@PathVariable String productName) {
        return service.checkProductAvailability(productName);
    }
}