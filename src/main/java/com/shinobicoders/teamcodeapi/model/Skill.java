package com.shinobicoders.teamcodeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill_name", unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "skills")
    private List<User> users;

    @JsonIgnore
    @ManyToMany(mappedBy = "skills")
    private List<Project> projects;
}
