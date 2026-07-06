package com.example.jwtdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if (AppUser.USERNAME.equals(request.getUsername())
                && AppUser.PASSWORD.equals(request.getPassword())) {

            String token =
                    jwtUtil.generateToken(request.getUsername());

            return ResponseEntity.ok(new LoginResponse(token));

        }

        return ResponseEntity.status(401)
                .body("Invalid credentials");

    }

}