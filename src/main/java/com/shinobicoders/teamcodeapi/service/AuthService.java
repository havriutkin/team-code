package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.auth.JWTUtils;
import com.shinobicoders.teamcodeapi.auth.LoginResponse;
import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(String email, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String token = jwtUtils.issueToken(
                String.valueOf(userPrincipal.getUserId()),
                userPrincipal.getEmail(),
                "ROLE_USER");

        return LoginResponse.builder()
                .token(token)
                .build();
    }

    public LoginResponse register(User user) {
        // Encrypt password
        var password = user.getPassword();
        var encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));

        userService.createUser(user);
        return login(user.getEmail(), password);
    }
}
