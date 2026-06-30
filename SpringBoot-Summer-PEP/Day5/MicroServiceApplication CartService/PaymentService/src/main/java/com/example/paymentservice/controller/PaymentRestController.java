package com.example.paymentservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentRestController {

    @GetMapping("/payment")
    public String payment() {
        return "Payment Service Running";
    }

}