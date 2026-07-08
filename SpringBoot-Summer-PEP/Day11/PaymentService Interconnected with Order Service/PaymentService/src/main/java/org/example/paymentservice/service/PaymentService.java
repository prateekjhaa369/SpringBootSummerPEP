package org.example.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    @Autowired
    private RestTemplate restTemplate;

    public String processPayment(String product) {

        String stockStatus = restTemplate.getForObject(
                "http://localhost:8082/inventory/check/" + product,
                String.class);

        if ("Available".equals(stockStatus)) {
            return "Payment Successful";
        }

        return "Payment Failed. Product Not Available";
    }
}