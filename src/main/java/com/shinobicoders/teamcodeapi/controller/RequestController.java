package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.model.Request;
import com.shinobicoders.teamcodeapi.service.AuthService;
import com.shinobicoders.teamcodeapi.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        // todo: add authService.authorizeUser

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

        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long id, @RequestBody Request request){
        if (!authService.authorizeRequestUser(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Request updatedRequest = requestService.updateRequest(id, request);

        if(updatedRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
}
