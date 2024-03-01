package com.shinobicoders.teamcodeapi.model;

import jakarta.persistence.*;

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

    private int participantsNumber;

    private int maxParticipantsNumber;

    private Date startDate;

    private String gitRepository;

    private ProjectLevel projectLevel;

    @OneToMany(mappedBy = "project")
    private List<Request> requests;

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

    public int getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public int getMaxParticipantsNumber() {
        return maxParticipantsNumber;
    }

    public void setMaxParticipantsNumber(int maxParticipantsNumber) {
        this.maxParticipantsNumber = maxParticipantsNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getGitRepository() {
        return gitRepository;
    }

    public void setGitRepository(String gitRepository) {
        this.gitRepository = gitRepository;
    }

    public ProjectLevel getProjectLevel() {
        return projectLevel;
    }

    public void setProjectLevel(ProjectLevel projectLevel) {
        this.projectLevel = projectLevel;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequest(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return  false;

        Project project = (Project) o;
        return Objects.equals(this.id, project.getId());

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
                ", participantNumber=" + participantsNumber +
                ", maxParticipantNumber=" + maxParticipantsNumber +
                ", startDate=" + startDate +
                ", gitRepository='" + gitRepository + '\'' +
                ", projectLevel=" + projectLevel +
                ", requests =" + requests +
                '}';
    }
}
