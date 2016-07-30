package com.teachit.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Choice.
 */
@Entity
@Table(name = "choice")
public class Choice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "response")
    private String response;

    @Column(name = "correct")
    private Boolean correct;

    @Column(name = "ordering")
    private Integer ordering;

    @ManyToOne
    private MultipleChoiceQuestion question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Boolean isCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public MultipleChoiceQuestion getQuestion() {
        return question;
    }

    public void setQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        this.question = multipleChoiceQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Choice choice = (Choice) o;
        if(choice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, choice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Choice{" +
            "id=" + id +
            ", response='" + response + "'" +
            ", correct='" + correct + "'" +
            ", ordering='" + ordering + "'" +
            '}';
    }
}
