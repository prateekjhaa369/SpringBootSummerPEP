package org.example.paymentservice.controller;

import org.example.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @GetMapping("/{product}")
    public String pay(@PathVariable String product) {
        return service.processPayment(product);
    }
}