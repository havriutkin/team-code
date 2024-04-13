package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.model.Skill;
import com.shinobicoders.teamcodeapi.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skill")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<List<Skill>> getSkills() {
        List<Skill> skills = skillService.getSkills();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<Skill> getSkillByName(String name) {
        Skill skill = skillService.getSkillByName(name);

        if (skill == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(skill, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Skill>> getSkillsByUserId(Long userId) {
        List<Skill> skills = skillService.getSkillsByUserId(userId);

        if (skills.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @GetMapping("/project")
    public ResponseEntity<List<Skill>> getSkillsByProjectId(Long projectId) {
        List<Skill> skills = skillService.getSkillsByProjectId(projectId);

        if (skills.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        Skill newSkill = skillService.createSkill(skill);
        return new ResponseEntity<>(newSkill, HttpStatus.CREATED);
    }
}
