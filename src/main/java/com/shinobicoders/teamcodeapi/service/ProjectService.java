package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.model.*;
import com.shinobicoders.teamcodeapi.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final SkillService skillService;
    private final UserService userService;

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id){
        return projectRepository.findById(id).orElse(null);
    }

    public List<Project> getProjectsByFilter (ProjectFilter projectFilter){
        String projectName = projectFilter.getName();
        ProjectLevel projectLevel = projectFilter.getProjectLevel();
        List<String> skillNames = projectFilter.getSkills() == null ?
                new ArrayList<>() :
                projectFilter.getSkills();

        List<Project> filteredProjects = new ArrayList<>(projectRepository.findAll());

        if (projectName != null) {
            filteredProjects.retainAll(projectRepository.findAllByNameContainingIgnoreCase(projectName));
        }

        if (projectLevel != null) {
            filteredProjects.retainAll(projectRepository.findAllByProjectLevel(projectLevel));
        }

        if (!skillNames.isEmpty()) {
            filteredProjects.retainAll(projectRepository.findAllProjectsBySkills(skillNames, skillNames.size()));
        }

        return filteredProjects;
    }

    public Project createProject(Project project){
        return projectRepository.save(project);
    }

    public Project addSkill(Long projectId, Long skillId){
        Project project = projectRepository.findById(projectId).orElse(null);
        Skill skill = skillService.getSkillById(skillId);

        if (project == null || skill == null) {
            return null;
        }

        List<Skill> skills = project.getSkills();
        skills.add(skill);
        project.setSkills(skills);

        return projectRepository.save(project);
    }

    public Project addParticipant(Long projectId, Long participantId){
        Project project = projectRepository.findById(projectId).orElse(null);
        User participant = userService.getUserById(participantId);

        if (project == null || participant == null) {
            return null;
        }

        List<User> participants = project.getParticipants();
        int oldParticipantsNumber = project.getParticipantsNumber();
        participants.add(participant);
        project.setParticipants(participants);
        project.setParticipantsNumber(oldParticipantsNumber + 1);

        return projectRepository.save(project);
    }

    @Transactional
    public Project updateProject(Long id, Project project){
        Project existingProject = projectRepository.findById(id).orElse(null);

        if (existingProject == null) {
            return null;
        }

        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setStatus(project.isStatus());
        existingProject.setParticipantsNumber(project.getParticipantsNumber());
        existingProject.setMaxParticipantsNumber(project.getMaxParticipantsNumber());
        existingProject.setGitRepository(project.getGitRepository());
        existingProject.setProjectLevel(project.getProjectLevel());

        return projectRepository.save(existingProject);
    }

    @Transactional
    public Project removeParticipant(Long projectId, Long participantId) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project == null) {
            return null;
        }

        List<User> participants = project.getParticipants();
        participants.removeIf(user -> user.getId().equals(participantId));
        project.setParticipants(participants);
        projectRepository.save(project);

        return project;
    }

    @Transactional
    public Project removeSkill(Long projectId, Long skillId){
        Project project = projectRepository.findById(projectId).orElse(null);
        Skill skill = skillService.getSkillById(skillId);

        if (project == null || skill == null) {
            return null;
        }

        List<Skill> skills = project.getSkills();
        skills.remove(skill);
        project.setSkills(skills);

        return projectRepository.save(project);
    }

    public void deleteProject(Long id){
        projectRepository.deleteById(id);
    }

}
