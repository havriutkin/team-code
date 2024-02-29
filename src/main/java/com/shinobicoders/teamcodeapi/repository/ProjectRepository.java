package com.shinobicoders.teamcodeapi.repository;

import com.shinobicoders.teamcodeapi.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    public Optional<Project> findProjectByName(String name);
}
