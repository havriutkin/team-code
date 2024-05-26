package com.shinobicoders.teamcodeapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueUserAndProject",
        columnNames = { "user_id", "project_id" }) })
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status_id")
    private RequestStatus status;

    private Date requestDate;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return  false;

        Request request = (Request) o;
        return Objects.equals(this.id, request.getId());
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", status=" + status +
                ", requestDate=" + requestDate +
                ", message='" + message + '\'' +
                ", user=" + user +
                ", project=" + project +
                '}';
    }
}
