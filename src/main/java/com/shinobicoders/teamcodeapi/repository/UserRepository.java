package com.shinobicoders.teamcodeapi.repository;

import com.shinobicoders.teamcodeapi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
