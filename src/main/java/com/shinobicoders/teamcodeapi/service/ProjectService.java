package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.model.ProjectFilter;
import com.shinobicoders.teamcodeapi.model.ProjectLevel;
import com.shinobicoders.teamcodeapi.model.Skill;
import com.shinobicoders.teamcodeapi.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id){
        return projectRepository.findById(id).orElse(null);
    }

    public Project createProject(Project project){
        return projectRepository.save(project);
    }

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

    public void deleteProject(Long id){
        projectRepository.deleteById(id);
    }
}
