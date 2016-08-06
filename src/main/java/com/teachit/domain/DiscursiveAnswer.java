package com.teachit.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DiscursiveAnswer.
 */
@Entity
@Table(name = "discursive_answer")
public class DiscursiveAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "answer")
    private String answer;

    @Column(name = "score")
    private Double score;

    @ManyToOne
    private DiscursiveQuestion question;

    @ManyToOne
    private Person student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public DiscursiveQuestion getQuestion() {
        return question;
    }

    public void setQuestion(DiscursiveQuestion discursiveQuestion) {
        this.question = discursiveQuestion;
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person person) {
        this.student = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiscursiveAnswer discursiveAnswer = (DiscursiveAnswer) o;
        if(discursiveAnswer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, discursiveAnswer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DiscursiveAnswer{" +
            "id=" + id +
            ", answer='" + answer + "'" +
            ", score='" + score + "'" +
            '}';
    }
}
