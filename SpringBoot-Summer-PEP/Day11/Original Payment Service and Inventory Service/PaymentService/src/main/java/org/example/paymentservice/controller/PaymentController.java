package org.example.paymentservice.controller;

import org.example.paymentservice.entity.Payment;
import org.example.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping
    public Payment savePayment(@RequestBody Payment payment) {
        return service.savePayment(payment);
    }
}