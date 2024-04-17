package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.auth.JWTUtils;
import com.shinobicoders.teamcodeapi.auth.LoginResponse;
import com.shinobicoders.teamcodeapi.model.Project;
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
    private final ProjectService projectService;
    private final RequestService requestService;
    private final NotificationService notificationService;

    public LoginResponse login(String email, String password) {
        User user = userService.getUserByEmail(email);

        String token = jwtUtils.issueToken(
                String.valueOf(user.getId()),
                user.getEmail(),
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

    public boolean authorizeProjectOwner(Long userId, Long projectId) {
        Project project = projectService.getProjectById(projectId);
        return project.getOwner().getId().equals(userId);
    }

    public boolean authorizeProjectMember(Long userId, Long projectId) {
        Project project = projectService.getProjectById(projectId);
        return project.getParticipants().stream().anyMatch(member -> member.getId().equals(userId));
    }

    public boolean authorizeRequestOwner(Long userId, Long requestId) {
        return requestService.getRequestById(requestId).getUser().getId().equals(userId);
    }

    public boolean authorizeNotificationOwner(Long userId, Long notificationId) {
        return notificationService.getNotificationById(notificationId).getUser().getId().equals(userId);
    }
}
