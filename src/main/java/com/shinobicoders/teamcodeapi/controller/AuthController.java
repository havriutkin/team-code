package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.auth.LoginRequest;
import com.shinobicoders.teamcodeapi.auth.LoginResponse;
import com.shinobicoders.teamcodeapi.auth.RegisterRequest;
import com.shinobicoders.teamcodeapi.auth.UserPrincipal;
import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.service.AuthService;
import com.shinobicoders.teamcodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return authService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/register")
    public LoginResponse register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return authService.register(user);
    }

    @GetMapping("/user")
    public UserPrincipal getUser() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
