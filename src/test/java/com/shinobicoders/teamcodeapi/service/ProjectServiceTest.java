package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.model.ProjectLevel;
import com.shinobicoders.teamcodeapi.model.ProjectFilter;
import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.model.Skill;
import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private SkillService skillService;

    @Mock
    private UserService userService;

    private Project projectMock;
    private User userMock;
    private Skill skillMock;

    @BeforeEach
    void setUp() {
        projectMock = new Project();
        projectMock.setId(1L);
        projectMock.setName("Test project");
        projectMock.setDescription("Test description");
        projectMock.setStartDate(new Date());
        projectMock.setProjectLevel(ProjectLevel.EASY);

        userMock = new User();
        userMock.setId(1L);
        userMock.setName("Test user");
        userMock.setEmail("usermail@gmail.com");

        skillMock = new Skill();
        skillMock.setId(1L);
        skillMock.setName("Test skill");

        projectMock.setParticipants(new ArrayList<>(List.of(userMock)));
        projectMock.setSkills(new ArrayList<>(List.of(skillMock)));
        userMock.setParticipatingProjects(new ArrayList<>(List.of(projectMock)));
        skillMock.setProjects(new ArrayList<>(List.of(projectMock)));
    }

    @Nested
    class GetAllProjectsTest {
        @Test
        void shouldReturnAllProjects() {
            List<Project> projects = List.of(projectMock);
            when(projectRepository.findAll()).thenReturn(projects);

            List<Project> result = projectService.getAllProjects();

            assertEquals(1, result.size());
            assertEquals(projectMock, result.get(0));
            verify(projectRepository, times(1)).findAll();
        }

        @Test
        void shouldReturnEmptyList() {
            List<Project> projects = List.of();
            when(projectRepository.findAll()).thenReturn(projects);

            List<Project> result = projectService.getAllProjects();

            assertEquals(0, result.size());
            verify(projectRepository, times(1)).findAll();
        }
    }

    @Nested
    class GetProjectByIdTest {
        @Test
        void shouldReturnProjectById() {
            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.of(projectMock));

            Project result = projectService.getProjectById(1L);

            assertEquals(projectMock, result);
            verify(projectRepository, times(1)).findById(1L);
        }

        @Test
        void shouldReturnNull() {
            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.empty());

            Project result = projectService.getProjectById(1L);

            assertNull(result);
            verify(projectRepository, times(1)).findById(1L);
        }
    }

    @Nested
    class GetProjectsByFilterTest {
        @Test
        void shouldReturnFilteredProjects() {
            ProjectFilter projectFilter = new ProjectFilter();
            projectFilter.setName("Test project");
            projectFilter.setProjectLevel(ProjectLevel.EASY);
            projectFilter.setSkills(List.of("Test skill"));

            when(projectRepository.findAll()).thenReturn(List.of(projectMock));
            when(projectRepository.findAllByNameContainingIgnoreCase("Test project")).thenReturn(List.of(projectMock));
            when(projectRepository.findAllByProjectLevel(ProjectLevel.EASY)).thenReturn(List.of(projectMock));
            when(projectRepository.findAllProjectsBySkills(List.of("Test skill"), 1)).thenReturn(List.of(projectMock));

            List<Project> result = projectService.getProjectsByFilter(projectFilter);

            assertEquals(1, result.size());
            assertEquals(projectMock, result.get(0));
            verify(projectRepository, times(1)).findAll();
            verify(projectRepository, times(1)).findAllByNameContainingIgnoreCase("Test project");
            verify(projectRepository, times(1)).findAllByProjectLevel(ProjectLevel.EASY);
            verify(projectRepository, times(1)).findAllProjectsBySkills(List.of("Test skill"), 1);
        }

        @Test
        void shouldReturnFilteredProjectsByName() {
            ProjectFilter projectFilter = new ProjectFilter();
            projectFilter.setName("Test project");

            when(projectRepository.findAll()).thenReturn(List.of(projectMock));
            when(projectRepository.findAllByNameContainingIgnoreCase("Test project")).thenReturn(List.of(projectMock));

            List<Project> result = projectService.getProjectsByFilter(projectFilter);

            assertEquals(1, result.size());
            assertEquals(projectMock, result.get(0));
            verify(projectRepository, times(1)).findAll();
            verify(projectRepository, times(1)).findAllByNameContainingIgnoreCase("Test project");
        }

        @Test
        void shouldReturnFilteredProjectsByProjectLevel() {
            ProjectFilter projectFilter = new ProjectFilter();
            projectFilter.setProjectLevel(ProjectLevel.EASY);

            when(projectRepository.findAll()).thenReturn(List.of(projectMock));
            when(projectRepository.findAllByProjectLevel(ProjectLevel.EASY)).thenReturn(List.of(projectMock));

            List<Project> result = projectService.getProjectsByFilter(projectFilter);

            assertEquals(1, result.size());
            assertEquals(projectMock, result.get(0));
            verify(projectRepository, times(1)).findAll();
            verify(projectRepository, times(1)).findAllByProjectLevel(ProjectLevel.EASY);
        }

        @Test
        void shouldReturnFilteredProjectsBySkills() {
            ProjectFilter projectFilter = new ProjectFilter();
            projectFilter.setSkills(List.of("Test skill"));

            when(projectRepository.findAll()).thenReturn(List.of(projectMock));
            when(projectRepository.findAllProjectsBySkills(List.of("Test skill"), 1)).thenReturn(List.of(projectMock));

            List<Project> result = projectService.getProjectsByFilter(projectFilter);

            assertEquals(1, result.size());
            assertEquals(projectMock, result.get(0));
            verify(projectRepository, times(1)).findAll();
            verify(projectRepository, times(1)).findAllProjectsBySkills(List.of("Test skill"), 1);
        }

        @Test
        void shouldReturnEmptyList() {
            ProjectFilter projectFilter = new ProjectFilter();
            projectFilter.setName("Test project");
            projectFilter.setProjectLevel(ProjectLevel.EASY);
            projectFilter.setSkills(List.of("Test skill"));

            when(projectRepository.findAll()).thenReturn(List.of());
            when(projectRepository.findAllByNameContainingIgnoreCase("Test project")).thenReturn(List.of(projectMock));
            when(projectRepository.findAllByProjectLevel(ProjectLevel.EASY)).thenReturn(List.of(projectMock));
            when(projectRepository.findAllProjectsBySkills(List.of("Test skill"), 1)).thenReturn(List.of(projectMock));

            List<Project> result = projectService.getProjectsByFilter(projectFilter);

            assertEquals(0, result.size());
            verify(projectRepository, times(1)).findAll();
            verify(projectRepository, times(1)).findAllByNameContainingIgnoreCase("Test project");
            verify(projectRepository, times(1)).findAllByProjectLevel(ProjectLevel.EASY);
            verify(projectRepository, times(1)).findAllProjectsBySkills(List.of("Test skill"), 1);
        }

        @Test
        void shouldReturnEmptyListBySkills() {
            ProjectFilter projectFilter = new ProjectFilter();
            projectFilter.setSkills(List.of("Wrong skill"));
            projectFilter.setProjectLevel(ProjectLevel.EASY);
            projectFilter.setName("Test project");

            when(projectRepository.findAll()).thenReturn(List.of(projectMock));
            when(projectRepository.findAllProjectsBySkills(List.of("Wrong skill"), 1)).thenReturn(List.of());
            when(projectRepository.findAllByNameContainingIgnoreCase("Test project")).thenReturn(List.of(projectMock));
            when(projectRepository.findAllByProjectLevel(ProjectLevel.EASY)).thenReturn(List.of(projectMock));

            List<Project> result = projectService.getProjectsByFilter(projectFilter);

            assertEquals(0, result.size());
            verify(projectRepository, times(1)).findAll();
            verify(projectRepository, times(1)).findAllProjectsBySkills(List.of("Wrong skill"), 1);
            verify(projectRepository, times(1)).findAllByNameContainingIgnoreCase("Test project");
            verify(projectRepository, times(1)).findAllByProjectLevel(ProjectLevel.EASY);
        }

        @Test
        void shouldReturnEmptyListByProjectLevel() {
            ProjectFilter projectFilter = new ProjectFilter();
            projectFilter.setProjectLevel(ProjectLevel.MEDIUM);
            projectFilter.setName("Test project");
            projectFilter.setSkills(List.of("Test skill"));

            when(projectRepository.findAll()).thenReturn(List.of(projectMock));
            when(projectRepository.findAllByProjectLevel(ProjectLevel.MEDIUM)).thenReturn(List.of());
            when(projectRepository.findAllByNameContainingIgnoreCase("Test project")).thenReturn(List.of(projectMock));
            when(projectRepository.findAllProjectsBySkills(List.of("Test skill"), 1)).thenReturn(List.of(projectMock));

            List<Project> result = projectService.getProjectsByFilter(projectFilter);

            assertEquals(0, result.size());
            verify(projectRepository, times(1)).findAll();
            verify(projectRepository, times(1)).findAllByProjectLevel(ProjectLevel.MEDIUM);
            verify(projectRepository, times(1)).findAllByNameContainingIgnoreCase("Test project");
            verify(projectRepository, times(1)).findAllProjectsBySkills(List.of("Test skill"), 1);
        }

        @Test
        void shouldReturnEmptyListByName() {
            ProjectFilter projectFilter = new ProjectFilter();
            projectFilter.setName("Wrong project");
            projectFilter.setProjectLevel(ProjectLevel.EASY);
            projectFilter.setSkills(List.of("Test skill"));

            when(projectRepository.findAll()).thenReturn(List.of(projectMock));
            when(projectRepository.findAllByNameContainingIgnoreCase("Wrong project")).thenReturn(List.of());
            when(projectRepository.findAllByProjectLevel(ProjectLevel.EASY)).thenReturn(List.of(projectMock));
            when(projectRepository.findAllProjectsBySkills(List.of("Test skill"), 1)).thenReturn(List.of(projectMock));

            List<Project> result = projectService.getProjectsByFilter(projectFilter);

            assertEquals(0, result.size());
            verify(projectRepository, times(1)).findAll();
            verify(projectRepository, times(1)).findAllByNameContainingIgnoreCase("Wrong project");
            verify(projectRepository, times(1)).findAllByProjectLevel(ProjectLevel.EASY);
            verify(projectRepository, times(1)).findAllProjectsBySkills(List.of("Test skill"), 1);
        }
    }

    @Nested
    class CreateProjectTest {
        @Test
        void shouldCreateProject() {
            projectService.createProject(projectMock);

            verify(projectRepository, times(1)).save(projectMock);
        }
    }

    @Nested
    class AddSkillTest {
        @Test
        void shouldAddSkill() {
            Skill newSkill = new Skill();
            newSkill.setId(2L);
            newSkill.setName("New skill");

            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.of(projectMock));
            when(skillService.getSkillById(2L)).thenReturn(newSkill);
            when(projectRepository.save(projectMock)).thenReturn(projectMock);

            Project result = projectService.addSkill(1L, 2L);

            assertEquals(projectMock, result);
            assertEquals(2, result.getSkills().size());
            assertTrue(result.getSkills().contains(skillMock));
            assertTrue(result.getSkills().contains(newSkill));

            verify(projectRepository, times(1)).findById(1L);
            verify(skillService, times(1)).getSkillById(2L);
            verify(projectRepository, times(1)).save(projectMock);
        }

        @Test
        void shouldReturnNull() {
            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.empty());
            when(skillService.getSkillById(1L)).thenReturn(skillMock);

            Project result = projectService.addSkill(1L, 1L);

            assertNull(result);
            verify(projectRepository, times(1)).findById(1L);
        }

        @Test
        void shouldReturnNullBySkill() {
            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.of(projectMock));
            when(skillService.getSkillById(1L)).thenReturn(null);

            Project result = projectService.addSkill(1L, 1L);

            assertNull(result);
            verify(projectRepository, times(1)).findById(1L);
            verify(skillService, times(1)).getSkillById(1L);
            verify(projectRepository, times(0)).save(projectMock);
        }
    }

    @Nested
    class AddParticipantTest {
        @Test
        void shouldAddParticipant() {
            User newUser = new User();
            newUser.setId(2L);
            newUser.setName("New user");
            newUser.setEmail("newemail@gmail.com");

            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.of(projectMock));
            when(userService.getUserById(2L)).thenReturn(newUser);
            when(projectRepository.save(projectMock)).thenReturn(projectMock);

            Project result = projectService.addParticipant(1L, 2L);

            assertEquals(projectMock, result);
            assertEquals(2, result.getParticipants().size());
            assertTrue(result.getParticipants().contains(userMock));
            assertTrue(result.getParticipants().contains(newUser));
            assertEquals(2, result.getParticipantsNumber());

            verify(projectRepository, times(1)).findById(1L);
            verify(userService, times(1)).getUserById(2L);
            verify(projectRepository, times(1)).save(projectMock);
        }

        @Test
        void shouldReturnNull() {
            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.empty());
            when(userService.getUserById(1L)).thenReturn(userMock);

            Project result = projectService.addParticipant(1L, 1L);

            assertNull(result);
            verify(projectRepository, times(1)).findById(1L);
        }
    }

    @Nested
    class UpdateProjectTest {
        @Test
        void shouldUpdateProject() {
            Project updatedProject = new Project();
            updatedProject.setId(1L);
            updatedProject.setName("Updated project");
            updatedProject.setDescription("Updated description");
            updatedProject.setStartDate(new Date());
            updatedProject.setProjectLevel(ProjectLevel.MEDIUM);

            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.of(projectMock));
            when(projectRepository.save(projectMock)).thenReturn(projectMock);

            Project result = projectService.updateProject(1L, updatedProject);

            assertEquals(projectMock, result);
            assertEquals("Updated project", result.getName());
            assertEquals("Updated description", result.getDescription());
            assertEquals(ProjectLevel.MEDIUM, result.getProjectLevel());

            verify(projectRepository, times(1)).findById(1L);
            verify(projectRepository, times(1)).save(projectMock);
        }

        @Test
        void shouldReturnNull() {
            Project updatedProject = new Project();
            updatedProject.setId(1L);
            updatedProject.setName("Updated project");
            updatedProject.setDescription("Updated description");
            updatedProject.setStartDate(new Date());
            updatedProject.setProjectLevel(ProjectLevel.MEDIUM);

            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.empty());

            Project result = projectService.updateProject(1L, updatedProject);

            assertNull(result);
            verify(projectRepository, times(1)).findById(1L);
            verify(projectRepository, times(0)).save(projectMock);
        }
    }

    @Nested
    class RemoveParticipantTest {
        @Test
        void shouldRemoveParticipant() {
            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.of(projectMock));

            Project result = projectService.removeParticipant(1L, 1L);

            assertEquals(projectMock, result);
            assertEquals(0, result.getParticipants().size());
            assertEquals(0, result.getParticipantsNumber());

            verify(projectRepository, times(1)).findById(1L);
            verify(projectRepository, times(1)).save(projectMock);
        }

        @Test
        void shouldReturnNull() {
            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.empty());

            Project result = projectService.removeParticipant(1L, 1L);

            assertNull(result);
            verify(projectRepository, times(1)).findById(1L);
            verify(projectRepository, times(0)).save(projectMock);
        }
    }

    @Nested
    class RemoveSkillTest {
        @Test
        void shouldRemoveSkill() {
            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.of(projectMock));
            when(skillService.getSkillById(1L)).thenReturn(skillMock);
            when(projectRepository.save(projectMock)).thenReturn(projectMock);

            Project result = projectService.removeSkill(1L, 1L);

            assertEquals(projectMock, result);
            assertEquals(0, result.getSkills().size());

            verify(projectRepository, times(1)).findById(1L);
            verify(skillService, times(1)).getSkillById(1L);
            verify(projectRepository, times(1)).save(projectMock);
        }

        @Test
        void shouldReturnNull() {
            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.empty());
            when(skillService.getSkillById(1L)).thenReturn(skillMock);

            Project result = projectService.removeSkill(1L, 1L);

            assertNull(result);
            verify(projectRepository, times(1)).findById(1L);
            verify(skillService, times(1)).getSkillById(1L);
        }

        @Test
        void shouldReturnNullBySkill() {
            when(projectRepository.findById(1L)).thenReturn(java.util.Optional.of(projectMock));
            when(skillService.getSkillById(1L)).thenReturn(null);

            Project result = projectService.removeSkill(1L, 1L);

            assertNull(result);
            verify(projectRepository, times(1)).findById(1L);
            verify(skillService, times(1)).getSkillById(1L);
            verify(projectRepository, times(0)).save(projectMock);
        }
    }

    @Nested
    class DeleteProjectTest {
        @Test
        void shouldDeleteProject() {
            projectService.deleteProject(1L);

            verify(projectRepository, times(1)).deleteById(1L);
        }
    }
}
