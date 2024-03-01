package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.model.Request;
import com.shinobicoders.teamcodeapi.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequest(@PathVariable Long id){
        return new ResponseEntity<>(requestService.getRequestById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request){
        return new ResponseEntity<>(requestService.createRequest(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Request> updateRequestMessage(@PathVariable Long id, @RequestBody String newMessage){
        return new ResponseEntity<>(requestService.updateRequestMessage(id, newMessage), HttpStatus.OK);
    }

    @DeleteMapping("/{id")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id){
        requestService.deleteRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
