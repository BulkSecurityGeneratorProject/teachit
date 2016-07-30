package com.teachit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "active")
    private Boolean active;

    @OneToOne
    @JoinColumn(unique = true)
    private ApplicationAdmission applicationAdmission;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    private Set<ContentView> views = new HashSet<>();

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private Set<Course> teachingCoursees = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "person_learning_coursees",
               joinColumns = @JoinColumn(name="people_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="learning_coursees_id", referencedColumnName="ID"))
    private Set<Course> learningCoursees = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ApplicationAdmission getApplicationAdmission() {
        return applicationAdmission;
    }

    public void setApplicationAdmission(ApplicationAdmission applicationAdmission) {
        this.applicationAdmission = applicationAdmission;
    }

    public Set<ContentView> getViews() {
        return views;
    }

    public void setViews(Set<ContentView> contentViews) {
        this.views = contentViews;
    }

    public Set<Course> getTeachingCoursees() {
        return teachingCoursees;
    }

    public void setTeachingCoursees(Set<Course> courses) {
        this.teachingCoursees = courses;
    }

    public Set<Course> getLearningCoursees() {
        return learningCoursees;
    }

    public void setLearningCoursees(Set<Course> courses) {
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
        Person person = (Person) o;
        if(person.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", active='" + active + "'" +
            '}';
    }
}
