package com.shinobicoders.teamcodeapi.repository;

import com.shinobicoders.teamcodeapi.model.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Long> {
    List<Request> findAllByUserId(Long userId);

    List<Request> findAllByProjectId(Long projectId);
}

