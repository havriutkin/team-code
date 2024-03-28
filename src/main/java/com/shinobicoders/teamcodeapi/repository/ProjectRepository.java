package com.shinobicoders.teamcodeapi.repository;

import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.model.ProjectLevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByNameContainingIgnoreCase(String name);

    List<Project> findAllByProjectLevel(ProjectLevel projectLevel);

    @Query("SELECT p FROM Project p JOIN p.skills s WHERE s.name IN :skillNames GROUP BY p HAVING COUNT(DISTINCT s) = :numberOfSkills")
    List<Project> findAllProjectsBySkills(@Param("skillNames") List<String> skillNames, @Param("numberOfSkills") long numberOfSkills);
}
