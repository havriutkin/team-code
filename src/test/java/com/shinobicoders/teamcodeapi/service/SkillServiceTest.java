package com.shinobicoders.teamcodeapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.shinobicoders.teamcodeapi.model.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shinobicoders.teamcodeapi.repository.SkillRepository;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {
    @InjectMocks
    private SkillService skillService;

    @Mock
    private SkillRepository skillRepository;

    private Skill skillMock;

    @BeforeEach
    public void setUp() {
        skillMock = new Skill();
        skillMock.setId(1L);
        skillMock.setName("Java");
        skillMock.setUsers(new ArrayList<>());
        skillMock.setProjects(new ArrayList<>());
    }

    @Nested
    class GetSkillsTests {
        @Test
        public void shouldReturnAllSkills() {
            List<Skill> skills = new ArrayList<>();
            skills.add(skillMock);
            when(skillRepository.findAll()).thenReturn(skills);

            List<Skill> result = skillService.getSkills();

            assertEquals(1, result.size());
            assertEquals(skillMock, result.get(0));
            verify(skillRepository, times(1)).findAll();
        }

        @Test
        public void shouldReturnEmptyList() {
            List<Skill> skills = new ArrayList<>();
            when(skillRepository.findAll()).thenReturn(skills);

            List<Skill> result = skillService.getSkills();

            assertEquals(0, result.size());
            verify(skillRepository, times(1)).findAll();
        }
    }

    @Nested
    class GetSkillByIdTests {
        @Test
        public void shouldReturnSkill() {
            when(skillRepository.findById(1L)).thenReturn(java.util.Optional.of(skillMock));

            Skill result = skillService.getSkillById(1L);

            assertEquals(skillMock, result);
            verify(skillRepository, times(1)).findById(1L);
        }

        @Test
        public void shouldReturnNull() {
            when(skillRepository.findById(1L)).thenReturn(java.util.Optional.empty());

            Skill result = skillService.getSkillById(1L);

            assertNull(result);
            verify(skillRepository, times(1)).findById(1L);
        }
    }

    @Nested
    class GetSkillsByUserIdTests {
        @Test
        public void shouldReturnSkills() {
            List<Skill> skills = new ArrayList<>();
            skills.add(skillMock);
            when(skillRepository.findSkillsByUserId(1L)).thenReturn(skills);

            List<Skill> result = skillService.getSkillsByUserId(1L);

            assertEquals(1, result.size());
            assertEquals(skillMock, result.get(0));
            verify(skillRepository, times(1)).findSkillsByUserId(1L);
        }

        @Test
        public void shouldReturnEmptyList() {
            List<Skill> skills = new ArrayList<>();
            when(skillRepository.findSkillsByUserId(1L)).thenReturn(skills);

            List<Skill> result = skillService.getSkillsByUserId(1L);

            assertEquals(0, result.size());
            verify(skillRepository, times(1)).findSkillsByUserId(1L);
        }
    }

    @Nested
    class GetSkillsByProjectIdTests {
        @Test
        public void shouldReturnSkills() {
            List<Skill> skills = new ArrayList<>();
            skills.add(skillMock);
            when(skillRepository.findSkillsByProjectId(1L)).thenReturn(skills);

            List<Skill> result = skillService.getSkillsByProjectId(1L);

            assertEquals(1, result.size());
            assertEquals(skillMock, result.get(0));
            verify(skillRepository, times(1)).findSkillsByProjectId(1L);
        }

        @Test
        public void shouldReturnEmptyList() {
            List<Skill> skills = new ArrayList<>();
            when(skillRepository.findSkillsByProjectId(1L)).thenReturn(skills);

            List<Skill> result = skillService.getSkillsByProjectId(1L);

            assertEquals(0, result.size());
            verify(skillRepository, times(1)).findSkillsByProjectId(1L);
        }
    }

    @Nested
    class GetSkillByNameTests {
        @Test
        public void shouldReturnSkill() {
            when(skillRepository.findByName("Java")).thenReturn(java.util.Optional.of(skillMock));

            Skill result = skillService.getSkillByName("Java");

            assertEquals(skillMock, result);
            verify(skillRepository, times(1)).findByName("Java");
        }

        @Test
        public void shouldReturnNull() {
            when(skillRepository.findByName("Java")).thenReturn(java.util.Optional.empty());

            Skill result = skillService.getSkillByName("Java");

            assertNull(result);
            verify(skillRepository, times(1)).findByName("Java");
        }
    }

    @Nested
    class CreateSkillTests {
        @Test
        public void shouldCreateSkill() {
            when(skillRepository.save(skillMock)).thenReturn(skillMock);

            Skill result = skillService.createSkill(skillMock);

            assertEquals(skillMock, result);
            verify(skillRepository, times(1)).save(skillMock);
        }
    }
}
