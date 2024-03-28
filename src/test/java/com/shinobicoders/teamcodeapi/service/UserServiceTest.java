package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.model.Experience;
import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Nested
    public class GetAllUsersTests {

        @Test
        public void shouldReturnAllUsers() {
            User user1 = new User();
            User user2 = new User();
            List<User> users = Arrays.asList(user1, user2);

            when(userRepository.findAll()).thenReturn(users);

            List<User> result = userService.getAllUsers();

            assertEquals(2, result.size());
            verify(userRepository, times(1)).findAll();
        }

        @Test
        public void shouldReturnEmptyList() {
            when(userRepository.findAll()).thenReturn(List.of());

            List<User> result = userService.getAllUsers();

            assertEquals(0, result.size());
            verify(userRepository, times(1)).findAll();
        }

    }

    @Nested
    public class GetUserByIdTests {
        @Test
        public void shouldReturnUserById() {
            User user = new User();
            user.setId(1L);

            when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

            User result = userService.getUserById(1L);

            assertEquals(1L, result.getId().longValue());
            verify(userRepository, times(1)).findById(1L);
        }

        @Test
        public void shouldThrowUserNotFound() {
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> userService.getUserById(1L));

            verify(userRepository, times(1)).findById(1L);
        }
    }

    @Nested
    public class GetUserByEmailTests {
        @Test
        public void shouldReturnUserByEmail() {
            User user = new User();
            String email = "fakemail@gmail.com";
            user.setEmail(email);

            when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

            User result = userService.getUserByEmail(email);

            assertEquals(email, result.getEmail());
            verify(userRepository, times(1)).findByEmail(email);
        }

        @Test
        public void shouldThrowUserNotFound() {
            String email = "fakamail@gmail.com";
            when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> userService.getUserByEmail(email));

            verify(userRepository, times(1)).findByEmail(email);
        }
    }

    @Nested
    public class CreateUserTests {
        @Test
        public void shouldCreateUser() {
            User user = new User();

            when(userRepository.save(user)).thenReturn(user);

            User result = userService.createUser(user);

            assertEquals(user, result);
            verify(userRepository, times(1)).save(user);
        }

        @Test
        public void shouldThrowErrorCreatingUser() {
            User user = new User();

            when(userRepository.save(user)).thenThrow(new RuntimeException("Error creating user"));

            assertThrows(RuntimeException.class, () -> userService.createUser(user));

            verify(userRepository, times(1)).save(user);
        }
    }

    @Nested
    public class UpdateUserTests {
        @Test
        public void shouldUpdateUser() {
            User user = new User();
            user.setId(1L);
            User userDetails = new User();
            userDetails.setName("John Doe");
            userDetails.setEmail("fakemail@gmail.com");
            userDetails.setPassword("password");
            userDetails.setBio("I am a software developer");
            userDetails.setGithubLink("fakelink.com");
            userDetails.setExperience(Experience.INTERMEDIATE);

            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            when(userRepository.save(user)).thenReturn(user);

            User result = userService.updateUser(1L, userDetails);

            assertEquals(userDetails.getName(), result.getName());
            assertEquals(userDetails.getEmail(), result.getEmail());
            assertEquals(userDetails.getPassword(), result.getPassword());
            assertEquals(userDetails.getBio(), result.getBio());
            assertEquals(userDetails.getGithubLink(), result.getGithubLink());
            assertEquals(userDetails.getExperience(), result.getExperience());

            verify(userRepository, times(1)).findById(1L);
            verify(userRepository, times(1)).save(user);
        }

        @Test
        public void shouldThrowUserNotFound() {
            User userDetails = new User();
            userDetails.setName("John Doe");
            userDetails.setEmail("fakemail@gmail.com");
            userDetails.setPassword("password");
            userDetails.setBio("I am a software developer");
            userDetails.setGithubLink("fakelink.com");
            userDetails.setExperience(Experience.INTERMEDIATE);

            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> userService.updateUser(1L, userDetails));

            verify(userRepository, times(1)).findById(1L);
        }

        @Test
        public void shouldThrowErrorUpdatingUser() {
            User user = new User();
            user.setId(1L);
            User userDetails = new User();
            userDetails.setName("John Doe");
            userDetails.setEmail("fakemail@gmail.com");
            userDetails.setPassword("password");
            userDetails.setBio("I am a software developer");
            userDetails.setGithubLink("fakelink.com");
            userDetails.setExperience(Experience.INTERMEDIATE);

            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            when(userRepository.save(user)).thenThrow(new RuntimeException("Error updating user"));

            assertThrows(RuntimeException.class, () -> userService.updateUser(1L, userDetails));

            verify(userRepository, times(1)).findById(1L);
            verify(userRepository, times(1)).save(user);
        }
    }

    @Nested
    public class DeleteUserTests {
        @Test
        public void shouldDeleteUser() {
            doNothing().when(userRepository).deleteById(1L);

            userService.deleteUser(1L);

            verify(userRepository, times(1)).deleteById(1L);
        }

        @Test
        public void shouldThrowUserNotFound() {
            doThrow(new RuntimeException("User not found with id: 1")).when(userRepository).deleteById(1L);

            assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));

            verify(userRepository, times(1)).deleteById(1L);
        }

        @Test
        public void shouldThrowErrorDeletingUser() {
            doThrow(new RuntimeException("Error deleting user")).when(userRepository).deleteById(1L);

            assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));

            verify(userRepository, times(1)).deleteById(1L);
        }
    }
}
