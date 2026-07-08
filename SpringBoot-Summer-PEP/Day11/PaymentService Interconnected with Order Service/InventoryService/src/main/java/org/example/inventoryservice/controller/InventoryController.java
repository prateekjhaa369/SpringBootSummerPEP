package org.example.inventoryservice.controller;

import org.example.inventoryservice.entity.Product;
import org.example.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class InventoryController {

    @Autowired
    private InventoryService service;

    // Create Product
    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    // Get All Products
    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    // Get Product By Id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    // Update Product
    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    // Delete Product
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return "Product Deleted Successfully";
    }
}