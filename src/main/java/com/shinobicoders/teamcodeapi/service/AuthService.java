package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.auth.JWTUtils;
import com.shinobicoders.teamcodeapi.auth.LoginResponse;
import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.model.Request;
import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
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

        if (user == null) {
            return null;
        }

        var encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return null;
        }

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
        try{
            userService.createUser(user);
        }
        catch (Exception e){
            return null;
        }
        return login(user.getEmail(), password);
    }

    public boolean authorizeProjectOwner(Long projectId) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return projectService.getProjectById(projectId).getOwner().getId().equals(principal.getUserId());
    }

    public boolean authorizeProjectMember(Long projectId) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Project project = projectService.getProjectById(projectId);
        return project.getParticipants().stream()
                        .anyMatch(user -> user.getId().equals(principal.getUserId()));
    }

    public boolean authorizeProjectMember(Long projectId, Long userId) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Check if principal is the member and userId equals principal's userId
        return authorizeProjectMember(projectId) && principal.getUserId().equals(userId);
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
