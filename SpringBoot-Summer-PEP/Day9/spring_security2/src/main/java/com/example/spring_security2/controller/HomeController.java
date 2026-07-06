package com.example.spring_security2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to public page";
    }

    @GetMapping("/user")
    public String userPage() {
        return "Welcome USER";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "Welcome ADMIN";
    }
}