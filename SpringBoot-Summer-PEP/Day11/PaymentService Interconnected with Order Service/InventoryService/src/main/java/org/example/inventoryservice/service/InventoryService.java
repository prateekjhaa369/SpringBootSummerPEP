package org.example.inventoryservice.service;

import org.example.inventoryservice.entity.Product;
import org.example.inventoryservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private ProductRepository repository;

    // Save Product
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    // Get All Products
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    // Get Product By Id
    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Update Product
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = repository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setQuantity(product.getQuantity());

            return repository.save(existingProduct);
        }

        return null;
    }

    // Delete Product
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    public String checkProductAvailability(String productName) {

        Product product = repository.findByProductName(productName).orElse(null);

        if (product != null && product.getQuantity() > 0) {
            return "Available";
        }

        return "Not Available";
    }
}