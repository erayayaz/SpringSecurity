package com.example.demo.controller;

import com.example.demo.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @PostMapping("/admin")
    public ResponseEntity<String> register(@RequestBody User request) {
        return ResponseEntity.ok("Admin Side");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test Side");
    }
}
