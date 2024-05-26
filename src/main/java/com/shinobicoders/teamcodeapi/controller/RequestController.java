package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.model.*;
import com.shinobicoders.teamcodeapi.service.AuthService;
import com.shinobicoders.teamcodeapi.service.NotificationService;
import com.shinobicoders.teamcodeapi.service.ProjectService;
import com.shinobicoders.teamcodeapi.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shinobicoders.teamcodeapi.model.RequestStatus;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {
    private final AuthService authService;
    private final RequestService requestService;
    private final NotificationService notificationService;
    private final ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequest(@PathVariable Long id){
        if (!authService.authorizeRequestUser(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Request request = requestService.getRequestById(id);

        if(request == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getRequestsByProjectId(@PathVariable Long projectId){
        if(!authService.authorizeProjectOwner(projectId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(requestService.getRequestsByProjectId(projectId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getRequestsByUserId(@PathVariable Long userId){
        if (!authService.authorizeUser(userId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(requestService.getRequestsByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request){
        request.setStatus(RequestStatus.PENDING);
        request.setRequestDate(new Date());

        Request createdRequest = requestService.createRequest(request);

        if(createdRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Create notification for project owner
        Project project = createdRequest.getProject();
        User owner = project.getOwner();
        Notification notification = new Notification();
        notification.setMessage("New request for project " + project.getName());
        notification.setUser(owner);
        notificationService.createNotification(notification);

        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long id, @RequestBody Request request){
        if (!authService.authorizeRequestUser(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Request odlRequest = requestService.getRequestById(id);
        Request updatedRequest = requestService.updateRequest(id, request);

        if(updatedRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Add user to project if request is approved
        if (updatedRequest.getStatus() == RequestStatus.APPROVED) {
            Project project = updatedRequest.getProject();
            Project updatedProject = projectService.addParticipant(project.getId(), updatedRequest.getUser().getId());

            if (updatedProject == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        // Create notification for user who made the request if the status has changed
        if (odlRequest.getStatus() != updatedRequest.getStatus()) {
            Notification notification = new Notification();
            notification.setMessage("Request for project " + updatedRequest.getProject().getName() + " has been updated");
            notification.setUser(updatedRequest.getUser());
            notificationService.createNotification(notification);
        }

        return  new ResponseEntity<>(updatedRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id){
        if (!authService.authorizeRequestUser(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        requestService.deleteRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/isExists")
    public ResponseEntity<?> isExists(@RequestParam Long projectId, @RequestParam Long userId){
        return  new ResponseEntity<>(requestService.isRequestExist(projectId, userId), HttpStatus.OK);
    }
}
