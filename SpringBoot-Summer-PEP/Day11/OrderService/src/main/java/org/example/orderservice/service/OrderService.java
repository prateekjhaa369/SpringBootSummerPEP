package org.example.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    public String placeOrder(String product) {

        String paymentStatus = restTemplate.getForObject(
                "http://localhost:8081/payments/" + product,
                String.class);

        if ("Payment Successful".equals(paymentStatus)) {
            return "Order Placed Successfully";
        }

        return "Order Not Placed";
    }
}