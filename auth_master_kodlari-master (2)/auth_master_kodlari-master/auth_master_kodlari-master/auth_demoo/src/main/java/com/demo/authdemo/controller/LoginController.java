package com.demo.authdemo.controller;


import com.demo.authdemo.entity.User;
import com.demo.authdemo.requests.LoginRequest;
import com.demo.authdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest user) {
        boolean success = userService.validateUser(user);
        if (success) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.badRequest().body("Login Failed");
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


}