package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.auth.UserPrincipal;
import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.model.ProjectFilter;
import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.service.AuthService;
import com.shinobicoders.teamcodeapi.service.ProjectService;
import com.shinobicoders.teamcodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final AuthService authService;
    private final ProjectService projectService;
    private final UserService userService;

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

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Project>> getProjectsByMemberId(@PathVariable Long memberId){
        User user = userService.getUserById(memberId);
        List<Project> projects = user.getParticipatingProjects();

        if (projects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Project>> getProjectsByOwnerId(@PathVariable Long ownerId){
        if(!authService.authorizeProjectOwner(ownerId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.getUserById(ownerId);
        List<Project> projects = user.getOwnedProjects();

        if (projects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(Project project){
        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        project.setStartDate(new Date());
        project.setOwner(owner);
        project.setParticipants(List.of(owner));
        project.setParticipantsNumber(1);
        project.setStatus(true);

        return new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
    }

    @PostMapping("/{projectId}/skill/{skillId}")
    public ResponseEntity<Project> addSkill(@PathVariable Long projectId, @PathVariable Long skillId){
        if(!authService.authorizeProjectOwner(projectId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Project project = projectService.addSkill(projectId, skillId);

        if (project == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(project, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails){
        if(!authService.authorizeProjectOwner(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Project updatedProject = projectService.updateProject(id, projectDetails);

        if (updatedProject == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id){
        if(!authService.authorizeProjectOwner(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/participant/{participantId}")
    public ResponseEntity<Project> removeParticipant(@PathVariable Long projectId, @PathVariable Long participantId){
        if(!authService.authorizeProjectOwner(projectId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Project project = projectService.removeParticipant(projectId, participantId);

        if (project == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/skill/{skillId}")
    public ResponseEntity<Project> removeSkill(@PathVariable Long projectId, @PathVariable Long skillId){
        if(!authService.authorizeProjectOwner(projectId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Project project = projectService.removeSkill(projectId, skillId);

        if (project == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(project, HttpStatus.OK);
    }
}
