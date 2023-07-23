package com.edu.app.controller;

import com.edu.app.entity.AuthRequest;
import com.edu.app.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        // Authenticate the user (You can implement your own authentication logic here)
        String username = authRequest.getUsername();

        // Generate JWT token
        String token = jwtUtil.generateToken(username);

        return token;
    }
}
