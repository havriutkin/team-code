package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.auth.LoginRequest;
import com.shinobicoders.teamcodeapi.auth.LoginResponse;
import com.shinobicoders.teamcodeapi.auth.RegisterRequest;
import com.shinobicoders.teamcodeapi.auth.UserPrincipal;
import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.service.AuthService;
import com.shinobicoders.teamcodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import com.shinobicoders.teamcodeapi.auth.UserPrincipal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest request) {
        LoginResponse loginResponse = authService.login(request.getEmail(), request.getPassword());
        if (loginResponse == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>( loginResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        LoginResponse loginResponse = authService.register(user);

        if (loginResponse == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user")
    public UserPrincipal getUser() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
