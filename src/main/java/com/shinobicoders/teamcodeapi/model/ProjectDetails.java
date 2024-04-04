package com.shinobicoders.teamcodeapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectDetails {
    private String name;
    private String description;
    private int maxParticipantsNumber;
    private String gitRepository;
    private ProjectLevel projectLevel;
    private List<String> skills;
}
