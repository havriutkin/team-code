package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.model.Request;
import com.shinobicoders.teamcodeapi.repository.RequestRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
        return requestRepository.findById(id).orElse(null);
    }

    public List<Request> getRequestsByUserId(Long userId){
        return requestRepository.findAllByUserId(userId);
    }

    public List<Request> getRequestsByProjectId(Long projectId){
        return requestRepository.findAllByProjectId(projectId);
    }

    public Request createRequest(Request request){
        return requestRepository.save(request);
    }

    @Transactional
    public Request updateRequest(Long id, Request updatedRequest){
        Request request = requestRepository.findById(id).orElse(null);
        if (request == null)
            return null;

        request.setStatus(updatedRequest.getStatus());
        request.setMessage(updatedRequest.getMessage());

        return request;
    }

    public void deleteRequest(Long id){
        requestRepository.deleteById(id);
    }
}
