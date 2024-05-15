package com.library.backend.controllers;

import com.library.backend.entities.User;
import com.library.backend.models.AuthenticationRequest;
import com.library.backend.security.CustomUserDetailsService;
import com.library.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        Map<String, Object> response = new HashMap<>();

        if(authService.authenticateUser(request.getUsername(), request.getPassword())){
            return ResponseEntity.ok(authService.login(request));
        }

        List<String> errors = new ArrayList<>();
        String error = "Credenciales inválidas.";
        errors.add(error);

        response.put("errors", errors);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user_actual")
    public User getCurrentUser(Principal principal){
        return (User) customUserDetailsService.loadUserByUsername(principal.getName());
    }
}
