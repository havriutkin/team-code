package com.shinobicoders.teamcodeapi.service;


import com.shinobicoders.teamcodeapi.auth.JWTUtils;
import com.shinobicoders.teamcodeapi.auth.LoginResponse;
import com.shinobicoders.teamcodeapi.auth.UserPrincipal;
import com.shinobicoders.teamcodeapi.model.Notification;
import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.model.Request;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.shinobicoders.teamcodeapi.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private ProjectService projectService;

    @Mock
    private RequestService requestService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private AuthService authService;

    private User authenticatedUserMock;
    private User userMock;
    private Project projectMock;
    private Request requestMock;
    private Notification notificationMock;

    @BeforeEach
    void setUp() {
        var encoder = new BCryptPasswordEncoder();

        authenticatedUserMock = new User();
        authenticatedUserMock.setId(1L);
        authenticatedUserMock.setEmail("authmail@gmail.com");
        authenticatedUserMock.setPassword(encoder.encode("password1"));

        userMock = new User();
        userMock.setId(2L);
        userMock.setEmail("usermail@gmail.com");
        userMock.setPassword(encoder.encode("password2"));

        projectMock = new Project();
        projectMock.setId(1L);
        projectMock.setOwner(authenticatedUserMock);
        projectMock.setParticipants(List.of(authenticatedUserMock));
        authenticatedUserMock.setOwnedProjects(List.of(projectMock));

        requestMock = new Request();
        requestMock.setId(1L);
        requestMock.setProject(projectMock);
        requestMock.setUser(userMock);
        userMock.setRequests(List.of(requestMock));

        notificationMock = new Notification();
        notificationMock.setId(1L);
        notificationMock.setMessage("Test message");
        notificationMock.setViewed(false);
        notificationMock.setUser(authenticatedUserMock);
        authenticatedUserMock.setNotifications(List.of(notificationMock));

        // Set up SecurityContext
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .userId(authenticatedUserMock.getId())
                .email(authenticatedUserMock.getEmail())
                .password(authenticatedUserMock.getPassword())
                .authorities(List.of())
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Nested
    class LoginTest {
        @BeforeEach
        void setUp() {
        }

        @Test
        void shouldReturnToken() {
            when(userService.getUserByEmail(authenticatedUserMock.getEmail())).thenReturn(authenticatedUserMock);
            when(jwtUtils.issueToken(
                    String.valueOf(authenticatedUserMock.getId()),
                    authenticatedUserMock.getEmail(),
                    "ROLE_USER")).thenReturn("token");

            var response = authService.login(authenticatedUserMock.getEmail(), "password1");
            assertNotNull(response);
            assertEquals("token", response.getToken());

            verify(userService, times(1)).getUserByEmail(authenticatedUserMock.getEmail());
        }

        @Test
        void shouldReturnNullIfUserNotFound() {
            var response = authService.login("fakemail@gmail.com", "password1");
            assertNull(response);

            verify(userService, times(1)).getUserByEmail("fakemail@gmail.com");
        }

        @Test
        void shouldReturnNullIfPasswordIncorrect() {
            when(userService.getUserByEmail(authenticatedUserMock.getEmail())).thenReturn(authenticatedUserMock);

            var response = authService.login(authenticatedUserMock.getEmail(), "wrongpassword");
            assertNull(response);

            verify(userService, times(1)).getUserByEmail(authenticatedUserMock.getEmail());
        }
    }

    @Nested
    class RegisterTest {
        @Test
        void shouldReturnToken() {
            when(userService.createUser(userMock)).thenReturn(userMock);

            authService.register(userMock);

            verify(userService, times(1)).createUser(userMock);
        }
    }

    @Nested
    class AuthorizeProjectOwnerTest {
        @BeforeEach
        void setUp() {
            when(projectService.getProjectById(projectMock.getId())).thenReturn(projectMock);
        }

        @Test
        void shouldReturnTrueIfUserIsOwner() {
            assertTrue(authService.authorizeProjectOwner(projectMock.getId()));
        }

        @Test
        void shouldReturnFalseIfUserIsNotOwner() {
            projectMock.setOwner(userMock);
            assertFalse(authService.authorizeProjectOwner(projectMock.getId()));
        }
    }

    @Nested
    class AuthorizeProjectMemberTest {
        private Project projectMock2;

        @BeforeEach
        void setUp() {
            projectMock2 = new Project();
            projectMock2.setId(2L);
            projectMock2.setOwner(userMock);
            projectMock2.setParticipants(List.of(userMock));
            userMock.setOwnedProjects(List.of(projectMock2));
        }

        @Test
        void shouldReturnTrueIfUserIsOwner() {
            when(projectService.getProjectById(projectMock.getId())).thenReturn(projectMock);
            assertTrue(authService.authorizeProjectMember(projectMock.getId()));
        }

        @Test
        void shouldReturnTrueIfUserIsParticipant() {
            when(projectService.getProjectById(projectMock.getId())).thenReturn(projectMock);
            projectMock.setOwner(userMock);
            assertTrue(authService.authorizeProjectMember(projectMock.getId()));
        }

        @Test
        void shouldReturnFalseIfUserIsNotOwnerOrParticipant() {
            when(projectService.getProjectById(projectMock2.getId())).thenReturn(projectMock2);
            assertFalse(authService.authorizeProjectMember(projectMock2.getId()));
        }
    }

    @Nested
    class AuthorizeRequestUserTest {
        @BeforeEach
        void setUp() {
            when(requestService.getRequestById(requestMock.getId())).thenReturn(requestMock);
        }

        @Test
        void shouldReturnTrueIfUserIsRequestOwner() {
            requestMock.setUser(authenticatedUserMock);
            assertTrue(authService.authorizeRequestUser(requestMock.getId()));
        }

        @Test
        void shouldReturnTrueIfUserIsProjectOwner() {
            assertTrue(authService.authorizeRequestUser(requestMock.getId()));
        }
    }

    @Nested
    class AuthorizeNotificationTest {
        @Test
        void shouldReturnTrueIfUserIsNotificationOwner() {
            when(notificationService.getNotificationById(notificationMock.getId())).thenReturn(notificationMock);
            assertTrue(authService.authorizeNotificationOwner(notificationMock.getId()));
        }
    }

    @Nested
    class AuthorizeUserTest {
        @Test
        void shouldReturnTrueIfUserIsAuthenticated() {
            assertTrue(authService.authorizeUser(authenticatedUserMock.getId()));
        }

        @Test
        void shouldReturnFalseIfUserIsNotAuthenticated() {
            assertFalse(authService.authorizeUser(userMock.getId()));
        }
    }
}
