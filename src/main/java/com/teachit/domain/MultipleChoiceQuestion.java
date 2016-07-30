package com.teachit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MultipleChoiceQuestion.
 */
@Entity
@Table(name = "multiple_choice_question")
public class MultipleChoiceQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ordering")
    private Integer ordering;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "question")
    private String question;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private Set<Choice> answers = new HashSet<>();

    @ManyToOne
    private Course course;

    @ManyToOne
    private Lesson lesson;

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<Choice> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Choice> choices) {
        this.answers = choices;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) o;
        if(multipleChoiceQuestion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, multipleChoiceQuestion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MultipleChoiceQuestion{" +
            "id=" + id +
            ", ordering='" + ordering + "'" +
            ", startDate='" + startDate + "'" +
            ", question='" + question + "'" +
            '}';
    }
}
