package com.shinobicoders.teamcodeapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class ProjectFilter {
    private String name;
    private ProjectLevel projectLevel;
    private List<String> skills;
}
