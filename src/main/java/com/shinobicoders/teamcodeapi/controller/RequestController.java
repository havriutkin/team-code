package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.model.Request;
import com.shinobicoders.teamcodeapi.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequest(@PathVariable Long id){
        Request request = requestService.getRequestById(id);

        if(request == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // todo: get by project id

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request){
        Request createdRequest = requestService.createRequest(request);

        if(createdRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long id, @RequestBody Request request){
        Request updatedRequest = requestService.updateRequest(id, request);

        if(updatedRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>(updatedRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id){
        requestService.deleteRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
