package com.teachit.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DiscursiveQuestion.
 */
@Entity
@Table(name = "discursive_question")
public class DiscursiveQuestion implements Serializable {

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
        DiscursiveQuestion discursiveQuestion = (DiscursiveQuestion) o;
        if(discursiveQuestion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, discursiveQuestion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DiscursiveQuestion{" +
            "id=" + id +
            ", ordering='" + ordering + "'" +
            ", startDate='" + startDate + "'" +
            ", question='" + question + "'" +
            '}';
    }
}
