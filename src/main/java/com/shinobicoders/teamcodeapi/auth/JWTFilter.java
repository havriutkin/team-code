package com.shinobicoders.teamcodeapi.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(jwtUtils::decodeToken)
                .map(jwtUtils::convertTokenToPrincipal)
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(authentication -> {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}
