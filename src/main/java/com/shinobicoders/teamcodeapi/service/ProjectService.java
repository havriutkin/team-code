package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.model.ProjectLevel;
import com.shinobicoders.teamcodeapi.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

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
        Project existingProject = projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project with ID [" + id + "] not found"));

        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setStatus(project.isStatus());
        existingProject.setParticipantsNumber(project.getParticipantsNumber());
        existingProject.setMaxParticipantsNumber(project.getMaxParticipantsNumber());
        existingProject.setStartDate(project.getStartDate());
        existingProject.setGitRepository(project.getGitRepository());
        existingProject.setProjectLevel(project.getProjectLevel());

        return projectRepository.save(existingProject);
    }

    public List<Project> FilterProjects (String projectName, ProjectLevel projectLevel, List<Long> skills){
        List<Project> filteredProjects = new ArrayList<>();
        
        if (projectName != null)
            filteredProjects.addAll(projectRepository.findAllByNameContainingIgnoreCase(projectName));

        if (projectLevel != null)
            filteredProjects.retainAll(projectRepository.findAllByProjectLevel(projectLevel));

        if (skills != null && !skills.isEmpty()) {
            filteredProjects.retainAll(projectRepository.findAllProjectsWithSkills(skills));
        }

        return  filteredProjects;
    }

    public void deleteProject(Long id){
        projectRepository.deleteById(id);
    }
}
