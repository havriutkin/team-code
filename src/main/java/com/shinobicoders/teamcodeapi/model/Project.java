package com.shinobicoders.teamcodeapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private boolean status;

    private int participantNumber;

    private int maxParticipantNumber;

    private Date startDate;

    private String githubRepository;

    private ProjectLevel projectLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getParticipantNumber() {
        return participantNumber;
    }

    public void setParticipantNumber(int participantNumber) {
        this.participantNumber = participantNumber;
    }

    public int getMaxParticipantNumber() {
        return maxParticipantNumber;
    }

    public void setMaxParticipantNumber(int maxParticipantNumber) {
        this.maxParticipantNumber = maxParticipantNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getGithubRepository() {
        return githubRepository;
    }

    public void setGithubRepository(String githubRepository) {
        this.githubRepository = githubRepository;
    }

    public ProjectLevel getProjectLevel() {
        return projectLevel;
    }

    public void setProjectLevel(ProjectLevel projectLevel) {
        this.projectLevel = projectLevel;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return  false;

        Project project = (Project) o;
        return Objects.equals(this.id, project.getId()) && this.status == project.isStatus() &&
                this.githubRepository.equals(project.getGithubRepository());

    }

    @Override
    public int hashCode(){
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", participantNumber=" + participantNumber +
                ", maxParticipantNumber=" + maxParticipantNumber +
                ", startDate=" + startDate +
                ", gitRepository='" + githubRepository + '\'' +
                ", projectLevel=" + projectLevel +
                '}';
    }
}
