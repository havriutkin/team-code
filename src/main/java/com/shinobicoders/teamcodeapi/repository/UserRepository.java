package com.shinobicoders.teamcodeapi.repository;

import com.shinobicoders.teamcodeapi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsUserByEmail(String email);
    boolean existsUserByName(String username);
}
