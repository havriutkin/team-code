package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.exception.EntityCreationException;
import com.shinobicoders.teamcodeapi.exception.EntityDeletionException;
import com.shinobicoders.teamcodeapi.exception.EntityUpdateException;
import com.shinobicoders.teamcodeapi.model.Request;
import com.shinobicoders.teamcodeapi.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

    public Request getRequestById(Long id) throws NotFoundException {
        return requestRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Request createRequest(Request request){
        List<Request> requests = getAllRequests();
        Optional<Request> existingRequest = requests.stream().filter(i -> i.equals(request)).findFirst();

        if(existingRequest.isPresent()) {
            // Duplicate request found, return null to indicate no creation
            return null;
        } else {
            // No duplicate found, save the new request and return it
            try{
                return requestRepository.save(request);
            } catch (Exception e){
                throw new EntityCreationException("Error creating request: " + e.getMessage(), e.getCause());
            }
        }
    }

    public Request updateRequest(Long id, Request updatedRequest) throws NotFoundException {
        Request request = requestRepository.findById(id).orElseThrow(NotFoundException::new);
        request.setStatus(updatedRequest.getStatus());
        try {
            return request;
        } catch (Exception e){
            throw new EntityUpdateException("Error updating request: " + e.getMessage(), e.getCause());
        }
    }

    public void deleteRequest(Long id) {
        try {
            requestRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityDeletionException("Error deleting request: " + e.getMessage(), e.getCause());
        }
    }
}
