package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.model.*;
import com.shinobicoders.teamcodeapi.service.AuthService;
import com.shinobicoders.teamcodeapi.service.NotificationService;
import com.shinobicoders.teamcodeapi.service.ProjectService;
import com.shinobicoders.teamcodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shinobicoders.teamcodeapi.model.Notification;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final AuthService authService;
    private final ProjectService projectService;
    private final UserService userService;
    private final NotificationService notificationService;

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
    public ResponseEntity<List<Project>> getProjectsByFilter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ProjectLevel projectLevel,
            @RequestParam(required = false) List<String> skills){
        ProjectFilter projectFilter = new ProjectFilter();
        projectFilter.setName(name);
        projectFilter.setProjectLevel(projectLevel);
        projectFilter.setSkills(skills);

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
        User user = userService.getUserById(ownerId);
        List<Project> projects = user.getOwnedProjects();

        if (projects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project){

        project.setStartDate(new Date());
        project.setParticipants(List.of());
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

    @PostMapping("/{projectId}/skills")
    public ResponseEntity<Project> addSkills(@PathVariable Long projectId, @RequestBody List<Long> skillIds){
        if(!authService.authorizeProjectOwner(projectId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Project project = null;
        for (Long skillId : skillIds) {
            project = projectService.addSkill(projectId, skillId);
        }

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
        if(!authService.authorizeProjectOwner(projectId) && !authService.authorizeProjectMember(projectId, participantId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Project project = projectService.removeParticipant(projectId, participantId);

        if (project == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        project.setParticipantsNumber(project.getParticipantsNumber() - 1);
        projectService.updateProject(project.getId(), project);

        // Create notification for project owner
        User owner = project.getOwner();
        User participant = userService.getUserById(participantId);
        Notification notification = new Notification();
        notification.setMessage("User " + participant.getName() + " left project " + project.getName());
        notification.setUser(owner);
        notificationService.createNotification(notification);

        // Notification for participant
        Notification notificationParticipant = new Notification();
        notificationParticipant.setMessage("You are not part of " + project.getName() + " any more.");
        notificationParticipant.setUser(participant);
        notificationService.createNotification(notificationParticipant);


        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/participants")
    public ResponseEntity<Project> removeParticipants(@PathVariable Long projectId, @RequestBody List<Long> participantIds){
        if(!authService.authorizeProjectOwner(projectId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Project project = null;
        for (Long participantId : participantIds) {
            project = projectService.removeParticipant(projectId, participantId);
        }


        if (project == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        project.setParticipantsNumber(project.getParticipantsNumber() - participantIds.size());
        projectService.updateProject(project.getId(), project);

        // Create notifications for project owner
        User owner = project.getOwner();
        for (Long participantId : participantIds) {
            User participant = userService.getUserById(participantId);
            Notification notification = new Notification();
            notification.setMessage("User " + participant.getName() + " left project " + project.getName());
            notification.setUser(owner);
            notificationService.createNotification(notification);

            // Notification for participant
            Notification notificationParticipant = new Notification();
            notificationParticipant.setMessage("You are not part of " + project.getName() + " any more.");
            notificationParticipant.setUser(participant);
            notificationService.createNotification(notificationParticipant);
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

    @DeleteMapping("/{projectId}/skills")
    public ResponseEntity<Project> removeSkills(@PathVariable Long projectId, @RequestBody List<Long> skillIds){
        if(!authService.authorizeProjectOwner(projectId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Project project = null;
        for (Long skillId : skillIds) {
            project = projectService.removeSkill(projectId, skillId);
        }

        if (project == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(project, HttpStatus.OK);
    }
}
