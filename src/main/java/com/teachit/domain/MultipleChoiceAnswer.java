package com.teachit.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MultipleChoiceAnswer.
 */
@Entity
@Table(name = "multiple_choice_answer")
public class MultipleChoiceAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Choice choosen;

    @ManyToOne
    private MultipleChoiceQuestion question;

    @ManyToOne
    private Person student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Choice getChoosen() {
        return choosen;
    }

    public void setChoosen(Choice choice) {
        this.choosen = choice;
    }

    public MultipleChoiceQuestion getQuestion() {
        return question;
    }

    public void setQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        this.question = multipleChoiceQuestion;
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
        MultipleChoiceAnswer multipleChoiceAnswer = (MultipleChoiceAnswer) o;
        if(multipleChoiceAnswer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, multipleChoiceAnswer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MultipleChoiceAnswer{" +
            "id=" + id +
            '}';
    }
}
