package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.model.*;
import com.shinobicoders.teamcodeapi.service.ProjectService;
import com.shinobicoders.teamcodeapi.service.RequestService;
import com.shinobicoders.teamcodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;

/*
* TODO:
*  - Authorize user before Get?
*  - Add try-catch block to handle exceptions, and return appropriate response
* */

@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequest(@PathVariable Long id){
        return new ResponseEntity<>(requestService.getRequestById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Request>> getRequestsByProjectId(@RequestParam Long projectId){
        return new ResponseEntity<>(requestService.getRequestByProjectId(projectId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody RequestDetails requestDetails) throws ChangeSetPersister.NotFoundException {
        // Get user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        // Get project
        Project project = projectService.getProjectById(requestDetails.getProjectId());

        Request request = new Request();
        request.setMessage(requestDetails.getMessage());
        request.setStatus(RequestStatus.PENDING);
        request.setRequestDate(new Date());
        request.setUser(user);
        request.setProject(project);


        return new ResponseEntity<>(requestService.createRequest(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long id, @RequestBody Request request) throws ChangeSetPersister.NotFoundException {
        // Get user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        // Check if user is owner of request
        if(!request.getUser().getId().equals(user.getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return  new ResponseEntity<>(requestService.updateRequest(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        // Get user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        // Check if user is owner of request
        Request request = requestService.getRequestById(id);
        if(!request.getUser().getId().equals(user.getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        requestService.deleteRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
