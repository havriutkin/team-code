package com.shinobicoders.teamcodeapi.repository;

import com.shinobicoders.teamcodeapi.model.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Long> {
}

