package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.shinobicoders.teamcodeapi.model.Skill;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    public List<Skill> getSkills() {
        return (List<Skill>) skillRepository.findAll();
    }

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id).orElse(null);
    }

    public List<Skill> getSkillsByUserId(Long userId) {
        return skillRepository.findSkillsByUserId(userId);
    }

    public List<Skill> getSkillsByProjectId(Long projectId) {
        return skillRepository.findSkillsByProjectId(projectId);
    }

    public Skill getSkillByName(String name) {
        return skillRepository.findByName(name).orElse(null);
    }

    // Create skill
    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }
}
