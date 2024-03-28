package com.shinobicoders.teamcodeapi.repository;
import com.shinobicoders.teamcodeapi.model.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkillRepository extends CrudRepository<Skill, Long>{
    @Query("SELECT s FROM Skill s JOIN s.users u WHERE u.id = :userId")
    List<Skill> findSkillsByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM Skill s JOIN s.projects p WHERE p.id = :projectId")
    List<Skill> findSkillsByProjectId(@Param("projectId") Long projectId);

    Skill findByName(String name);
}
