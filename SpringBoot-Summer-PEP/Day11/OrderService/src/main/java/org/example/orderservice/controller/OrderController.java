package org.example.orderservice.controller;

import org.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/{product}")
    public String placeOrder(@PathVariable String product) {
        return service.placeOrder(product);
    }
}