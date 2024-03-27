package com.shinobicoders.teamcodeapi.auth;

import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.auth.UserPrincipal;
import com.shinobicoders.teamcodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(username);

        return UserPrincipal.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }
}
