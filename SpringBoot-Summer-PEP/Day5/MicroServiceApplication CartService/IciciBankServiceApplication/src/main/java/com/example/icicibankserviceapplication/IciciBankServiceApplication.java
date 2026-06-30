package com.example.icicibankserviceapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class IciciBankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IciciBankServiceApplication.class, args);
    }

}