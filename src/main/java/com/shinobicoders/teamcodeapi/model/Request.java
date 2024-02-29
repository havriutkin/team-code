package com.shinobicoders.teamcodeapi.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RequestStatus status;

    private Date requestDate;

    private String message;

    @ManyToOne
    @JoinColumn(name = "request_user", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "request_project", referencedColumnName = "project_id")
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return  false;

        Request request = (Request) o;
        return Objects.equals(this.id, request.getId()) && this.status == request.getStatus()
                && this.getRequestDate().equals(request.getRequestDate())
                && this.getUser().equals(request.getUser())
                && this.getProject().equals(request.getProject())
                && this.hashCode() == request.hashCode();
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
