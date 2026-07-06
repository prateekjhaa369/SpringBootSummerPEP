package com.example.jwtdemo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        return ResponseEntity.ok(
                "Welcome, "
                        + username
                        + "! This is protected data.");

    }

}