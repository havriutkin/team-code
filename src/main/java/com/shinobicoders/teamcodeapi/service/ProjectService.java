package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id){
        return projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project with ID [" + id + "] not found"));
    }

    public  Project getProjectByName(String name){
        return projectRepository.findProjectByName(name).orElseThrow(() -> new EntityNotFoundException("Project with project name [" + name + "] not found"));
    }

    public Project createProject(Project project){
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project project){
        Project existingProject = projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project with ID [" + id + "] not found"));

        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setStatus(project.isStatus());
        existingProject.setParticipantNumber(project.getParticipantNumber());
        existingProject.setMaxParticipantNumber(project.getMaxParticipantNumber());
        existingProject.setStartDate(project.getStartDate());
        existingProject.setGithubRepository(project.getGithubRepository());
        existingProject.setProjectLevel(project.getProjectLevel());

        return projectRepository.save(existingProject);
    }

    public void deleteProject(Long id){
        projectRepository.deleteById(id);
    }
}
