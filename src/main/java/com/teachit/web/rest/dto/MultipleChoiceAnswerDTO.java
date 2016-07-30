package com.teachit.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the MultipleChoiceAnswer entity.
 */
public class MultipleChoiceAnswerDTO implements Serializable {

    private Long id;


    private Long choosenId;
    

    private String choosenResponse;

    private Long questionId;
    

    private String questionQuestion;

    private Long studentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChoosenId() {
        return choosenId;
    }

    public void setChoosenId(Long choiceId) {
        this.choosenId = choiceId;
    }


    public String getChoosenResponse() {
        return choosenResponse;
    }

    public void setChoosenResponse(String choiceResponse) {
        this.choosenResponse = choiceResponse;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long multipleChoiceQuestionId) {
        this.questionId = multipleChoiceQuestionId;
    }


    public String getQuestionQuestion() {
        return questionQuestion;
    }

    public void setQuestionQuestion(String multipleChoiceQuestionQuestion) {
        this.questionQuestion = multipleChoiceQuestionQuestion;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long personId) {
        this.studentId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MultipleChoiceAnswerDTO multipleChoiceAnswerDTO = (MultipleChoiceAnswerDTO) o;

        if ( ! Objects.equals(id, multipleChoiceAnswerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MultipleChoiceAnswerDTO{" +
            "id=" + id +
            '}';
    }
}
