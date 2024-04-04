package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.model.*;
import com.shinobicoders.teamcodeapi.service.ProjectService;
import com.shinobicoders.teamcodeapi.service.SkillService;
import com.shinobicoders.teamcodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(){
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id){
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Project>> getProjectsByFilter(@RequestBody ProjectFilter projectFilter){
        return new ResponseEntity<>(projectService.getProjectsByFilter(projectFilter), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectDetails projectDetails) throws ChangeSetPersister.NotFoundException {
        // Retrieve user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        List<Skill> skills = projectDetails.getSkills().stream().map(skillService::getSkillByName).toList();

        // Build project
        Project project = new Project();
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        project.setStatus(true);
        project.setParticipantsNumber(1);
        project.setMaxParticipantsNumber(projectDetails.getMaxParticipantsNumber());
        project.setStartDate(new Date());
        project.setGitRepository(projectDetails.getGitRepository());
        project.setProjectLevel(projectDetails.getProjectLevel());
        project.setOwner(user);
        project.setSkills(skills);
        project.setRequests(List.of());

        return new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project updatedProject) throws ChangeSetPersister.NotFoundException {
        // Get userEmail
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUsername();

        // Check if user is the owner of the project
        if(!projectService.isOwner(id, userEmail)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(projectService.updateProject(id, updatedProject), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        // Get userEmail
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUsername();

        // Check if user is the owner of the project
        if(!projectService.isOwner(id, userEmail)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
