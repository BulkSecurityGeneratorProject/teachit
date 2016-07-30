package com.teachit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ApplicationAdmission.
 */
@Entity
@Table(name = "application_admission")
public class ApplicationAdmission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @Column(name = "accepted")
    private Boolean accepted;

    @OneToOne(mappedBy = "applicationAdmission")
    @JsonIgnore
    private Person candidate;

    @OneToOne(mappedBy = "applicationAdmission")
    @JsonIgnore
    private Course course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public Boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Person getCandidate() {
        return candidate;
    }

    public void setCandidate(Person person) {
        this.candidate = person;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApplicationAdmission applicationAdmission = (ApplicationAdmission) o;
        if(applicationAdmission.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, applicationAdmission.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApplicationAdmission{" +
            "id=" + id +
            ", requestDate='" + requestDate + "'" +
            ", accepted='" + accepted + "'" +
            '}';
    }
}
