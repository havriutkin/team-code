package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.auth.JWTUtils;
import com.shinobicoders.teamcodeapi.auth.LoginResponse;
import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.model.Request;
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

    public boolean authorizeProjectOwner(Long projectId) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return projectService.getProjectById(projectId).getOwner().getId().equals(principal.getUserId());
    }

    public boolean authorizeProjectMember(Long projectId) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Project project = projectService.getProjectById(projectId);
        return project.getOwner().getId().equals(principal.getUserId()) ||
                project.getParticipants().stream()
                        .anyMatch(user -> user.getId().equals(principal.getUserId()));
    }

    public boolean authorizeRequestUser(Long requestId) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Request request = requestService.getRequestById(requestId);
        Project project = request.getProject();

        // Check if the user is the owner of the request
        if (request.getUser().getId().equals(principal.getUserId())) {
            return true;
        }

        // Check if the user is the owner of the project
        return project.getOwner().getId().equals(principal.getUserId());
    }

    public boolean authorizeNotificationOwner(Long notificationId) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return notificationService.getNotificationById(notificationId).getUser().getId().equals(principal.getUserId());
    }

    public boolean authorizeUser(Long userId) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUserId().equals(userId);
    }
}
