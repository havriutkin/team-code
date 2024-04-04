package com.shinobicoders.teamcodeapi.repository;

import com.shinobicoders.teamcodeapi.model.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends CrudRepository<Request, Long> {
    Optional<List<Request>> findByProjectId(Long projectId);
}

