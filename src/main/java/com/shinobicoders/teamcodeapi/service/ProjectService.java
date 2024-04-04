package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.exception.EntityCreationException;
import com.shinobicoders.teamcodeapi.exception.EntityDeletionException;
import com.shinobicoders.teamcodeapi.exception.EntityUpdateException;
import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.model.ProjectFilter;
import com.shinobicoders.teamcodeapi.model.ProjectLevel;
import com.shinobicoders.teamcodeapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) throws NotFoundException
    {
        return projectRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Project createProject(Project project){
        try{
            return projectRepository.save(project);
        } catch (Exception e){
            throw new EntityCreationException("Error creating project: " + e.getMessage(), e.getCause());
        }
    }

    public Project updateProject(Long id, Project project) throws NotFoundException
    {
        Project existingProject = projectRepository.findById(id).orElseThrow(NotFoundException::new);

        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setStatus(project.isStatus());
        existingProject.setParticipantsNumber(project.getParticipantsNumber());
        existingProject.setMaxParticipantsNumber(project.getMaxParticipantsNumber());
        existingProject.setStartDate(project.getStartDate());
        existingProject.setGitRepository(project.getGitRepository());
        existingProject.setProjectLevel(project.getProjectLevel());

        try {
            return projectRepository.save(existingProject);
        } catch (Exception e){
            throw new EntityUpdateException("Error updating project: " + e.getMessage(), e.getCause());
        }
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

    public void deleteProject(Long id) {
        try {
            projectRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityDeletionException("Error deleting project: " + e.getMessage(), e.getCause());
        }
    }
}
