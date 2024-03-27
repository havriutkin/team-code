package com.shinobicoders.teamcodeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.shinobicoders.teamcodeapi.model.Request;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name")
    private String name;

    private String description;

    private boolean status;

    private int participantsNumber;

    private int maxParticipantsNumber;

    private Date startDate;

    private String gitRepository;

    @Column(name = "project_level_id")
    private ProjectLevel projectLevel;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "project_skill",
            joinColumns = @JoinColumn(name = "fk_project"),
            inverseJoinColumns = @JoinColumn(name = "fk_skill")
    )
    private List<Skill> skills;

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private List<Request> requests;


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
