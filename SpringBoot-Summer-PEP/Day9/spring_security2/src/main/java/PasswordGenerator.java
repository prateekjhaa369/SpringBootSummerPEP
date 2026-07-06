package com.example.spring_security2;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("12345 -> " + encoder.encode("12345"));
        System.out.println("admin123 -> " + encoder.encode("admin123"));
    }
}