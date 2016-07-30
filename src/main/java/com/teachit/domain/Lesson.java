package com.teachit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lesson.
 */
@Entity
@Table(name = "lesson")
public class Lesson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ordering")
    private Integer ordering;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "lesson")
    @JsonIgnore
    private Set<MultipleChoiceQuestion> multipleChoices = new HashSet<>();

    @OneToMany(mappedBy = "lesson")
    @JsonIgnore
    private Set<DiscursiveQuestion> discursiveAnswers = new HashSet<>();

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private Set<Content> contents = new HashSet<>();

    @ManyToMany(mappedBy = "lessons")
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
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

    public Set<MultipleChoiceQuestion> getMultipleChoices() {
        return multipleChoices;
    }

    public void setMultipleChoices(Set<MultipleChoiceQuestion> multipleChoiceQuestions) {
        this.multipleChoices = multipleChoiceQuestions;
    }

    public Set<DiscursiveQuestion> getDiscursiveAnswers() {
        return discursiveAnswers;
    }

    public void setDiscursiveAnswers(Set<DiscursiveQuestion> discursiveQuestions) {
        this.discursiveAnswers = discursiveQuestions;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lesson lesson = (Lesson) o;
        if(lesson.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lesson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Lesson{" +
            "id=" + id +
            ", ordering='" + ordering + "'" +
            ", startDate='" + startDate + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
