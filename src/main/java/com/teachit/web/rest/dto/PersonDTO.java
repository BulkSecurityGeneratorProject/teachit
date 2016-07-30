package com.teachit.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Person entity.
 */
public class PersonDTO implements Serializable {

    private Long id;

    private Boolean active;


    private Long applicationAdmissionId;
    
    private Set<CourseDTO> learningCoursees = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getApplicationAdmissionId() {
        return applicationAdmissionId;
    }

    public void setApplicationAdmissionId(Long applicationAdmissionId) {
        this.applicationAdmissionId = applicationAdmissionId;
    }

    public Set<CourseDTO> getLearningCoursees() {
        return learningCoursees;
    }

    public void setLearningCoursees(Set<CourseDTO> courses) {
        this.learningCoursees = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonDTO personDTO = (PersonDTO) o;

        if ( ! Objects.equals(id, personDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
            "id=" + id +
            ", active='" + active + "'" +
            '}';
    }
}
