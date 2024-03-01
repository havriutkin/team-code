package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.model.Request;
import com.shinobicoders.teamcodeapi.repository.RequestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public List<Request> getAllRequests(){
        return (List<Request>) requestRepository.findAll();
    }

    public Request getRequestById(Long id){
        return requestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Request with ID [" + id + "] not found"));
    }

    public Request createRequest(Request request){
        List<Request> requests = getAllRequests();
        Optional<Request> existingRequest = requests.stream().filter(i -> i.getProject().equals(request.getProject())
                && i.getUser().equals(request.getUser())).findFirst();

        if(existingRequest.isPresent()) {
            // Duplicate request found, return null to indicate no creation
            return null;
        } else {
            // No duplicate found, save the new request and return it
            return requestRepository.save(request);
        }
    }

    public Request updateRequestMessage(Long id, String newMessage){
        Request request = requestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Request with ID [" + id + "] not found"));
        request.setMessage(newMessage);
        return request;
    }

    public void deleteRequest(Long id){
        requestRepository.deleteById(id);
    }
}
