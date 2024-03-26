package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(){
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id){
        System.out.println("HELLO BLYAT!!!!!");
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    /*
    @GetMapping("/{name}")
    public ResponseEntity<Project> getProjectByName(@PathVariable String name){
        return new ResponseEntity<>(projectService.getProjectByName(name), HttpStatus.OK);
    }*/

    @PostMapping
    public ResponseEntity<Project> createProject(Project project){
        return new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project updatedProject){
        return new ResponseEntity<>(projectService.updateProject(id, updatedProject), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}