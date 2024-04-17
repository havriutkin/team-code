package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.auth.UserPrincipal;
import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.model.ProjectFilter;
import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.service.AuthService;
import com.shinobicoders.teamcodeapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final AuthService authService;
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(){
        if (projectService.getAllProjects().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id){
        Project project = projectService.getProjectById(id);

        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Project>> getProjectsByFilter(@RequestBody ProjectFilter projectFilter){
        List<Project> projects = projectService.getProjectsByFilter(projectFilter);

        if (projects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Transient fields for skillNames, default set up owner
    @PostMapping
    public ResponseEntity<Project> createProject(Project project){
        return new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails){
        Project updatedProject = projectService.updateProject(id, projectDetails);

        if (updatedProject == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
