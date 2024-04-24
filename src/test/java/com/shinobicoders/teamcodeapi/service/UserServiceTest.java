package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.model.Experience;
import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.model.Skill;
import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
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
    private SkillService skillService;

    @Mock
    private UserRepository userRepository;

    private User userMock;
    private Skill skillMock;
    private Project projectMock;

    @BeforeEach
    public void setUpUserMock() {
        userMock = new User();
        userMock.setId(1L);
        userMock.setName("John Doe");
        userMock.setEmail("fakemail@gmail.com");
        userMock.setPassword("password");
        userMock.setBio("I am a software developer");
        userMock.setGithubLink("fakelink.com");
        userMock.setExperience(Experience.INTERMEDIATE);
        userMock.setSkills(new ArrayList<>());
        userMock.setParticipatingProjects(new ArrayList<>());
        userMock.setOwnedProjects(new ArrayList<>());
        userMock.setRequests(new ArrayList<>());
    }

    @BeforeEach
    public void setUpSkillMock() {
        skillMock = new Skill();
        skillMock.setId(1L);
        skillMock.setName("Java");
        skillMock.setUsers(new ArrayList<>());
        skillMock.setProjects(new ArrayList<>());
    }

    @BeforeEach
    public void setUpProjectMock() {
        projectMock = new Project();
        projectMock.setId(1L);
        projectMock.setName("Project 1");
        projectMock.setParticipants(new ArrayList<>());
        projectMock.setStatus(true);
        projectMock.setRequests(new ArrayList<>());
    }

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
        public void shouldReturnUserById() throws ChangeSetPersister.NotFoundException {
            when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userMock));

            User result = userService.getUserById(1L);

            assertEquals(userMock.getId(), result.getId().longValue());
            verify(userRepository, times(1)).findById(1L);
        }

        @Test
        public void shouldReturnNull() {
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            User result = userService.getUserById(1L);

            assertNull(result);
            verify(userRepository, times(1)).findById(1L);
        }
    }

    @Nested
    public class GetUserByEmailTests {
        @Test
        public void shouldReturnUserByEmail() {
            String email = userMock.getEmail();

            when(userRepository.findByEmail(email)).thenReturn(Optional.of(userMock));

            User result = userService.getUserByEmail(email);

            assertEquals(email, result.getEmail());
            verify(userRepository, times(1)).findByEmail(email);
        }

        @Test
        public void shouldReturnNull() {
            String email = userMock.getEmail();

            when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

            User result = userService.getUserByEmail(email);

            assertNull(result);
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
            User userDetails = new User();
            userDetails.setName("John Doe");
            userDetails.setEmail("fakemail@gmail.com");
            userDetails.setPassword("password");
            userDetails.setBio("I am a software developer");
            userDetails.setGithubLink("fakelink.com");
            userDetails.setExperience(Experience.INTERMEDIATE);

            when(userRepository.findById(1L)).thenReturn(Optional.of(userMock));
            when(userRepository.save(userMock)).thenReturn(userMock);

            User result = userService.updateUser(1L, userDetails);

            assertEquals(userDetails.getName(), result.getName());
            assertEquals(userDetails.getEmail(), result.getEmail());
            assertEquals(userDetails.getPassword(), result.getPassword());
            assertEquals(userDetails.getBio(), result.getBio());
            assertEquals(userDetails.getGithubLink(), result.getGithubLink());
            assertEquals(userDetails.getExperience(), result.getExperience());

            verify(userRepository, times(1)).findById(1L);
            verify(userRepository, times(1)).save(userMock);
        }

        @Test
        public void shouldReturnNull() {
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            User result = userService.updateUser(1L, userMock);

            assertNull(result);
            verify(userRepository, times(1)).findById(1L);
        }

        @Test
        public void shouldThrowErrorUpdatingUser() {
            User userDetails = new User();
            userDetails.setName("John Doe");
            userDetails.setEmail("fakemail@gmail.com");
            userDetails.setPassword("password");
            userDetails.setBio("I am a software developer");
            userDetails.setGithubLink("fakelink.com");
            userDetails.setExperience(Experience.INTERMEDIATE);

            when(userRepository.findById(1L)).thenReturn(Optional.of(userMock));
            when(userRepository.save(userMock)).thenThrow(new RuntimeException("Error updating user"));

            assertThrows(RuntimeException.class, () -> userService.updateUser(1L, userDetails));

            verify(userRepository, times(1)).findById(1L);
            verify(userRepository, times(1)).save(userMock);
        }
    }

    @Nested
    public class UserSkillTests {
        @Test
        public void shouldAddSkillToUser() {
            when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userMock));
            when(skillService.getSkillById(1L)).thenReturn(skillMock);
            when(userRepository.save(userMock)).thenReturn(userMock);

            User result = userService.addSkill(userMock.getId(), skillMock.getId());

            assertEquals(1, result.getSkills().size());
            assertTrue(result.getSkills().contains(skillMock));

            verify(userRepository, times(1)).findById(1L);
            verify(skillService, times(1)).getSkillById(1L);
            verify(userRepository, times(1)).save(userMock);
        }

        @Test
        public void shouldReturnNull() {
            when(userRepository.findById(1L)).thenReturn(Optional.empty());
            when(skillService.getSkillById(1L)).thenReturn(skillMock);

            User result = userService.addSkill(1L, 1L);

            assertNull(result);
            verify(userRepository, times(1)).findById(1L);
        }

        @Test
        public void shouldRemoveSkillFromUser() {
            userMock.getSkills().add(skillMock);

            when(userRepository.findById(1L)).thenReturn(Optional.of(userMock));
            when(userRepository.save(userMock)).thenReturn(userMock);

            User result = userService.removeSkill(1L, 1L);

            assertEquals(0, result.getSkills().size());
            verify(userRepository, times(1)).findById(1L);
            verify(userRepository, times(1)).save(userMock);
        }
    }

    @Nested
    public class ParticipatingProjectTests {
        @Test
        public void shouldLeaveProject() {
            userMock.getParticipatingProjects().add(projectMock);

            when(userRepository.findById(1L)).thenReturn(Optional.of(userMock));
            when(userRepository.save(userMock)).thenReturn(userMock);

            User result = userService.leaveProject(userMock.getId(), projectMock.getId());

            assertEquals(0, result.getParticipatingProjects().size());
            assertEquals(0, projectMock.getParticipants().size());

            verify(userRepository, times(1)).findById(1L);
            verify(userRepository, times(1)).save(userMock);
        }

        @Test
        public void shouldReturnNull() {
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            User result = userService.leaveProject(1L, 1L);

            assertNull(result);
            verify(userRepository, times(1)).findById(1L);
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
