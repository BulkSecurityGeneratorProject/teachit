package com.teachit.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Choice entity.
 */
public class ChoiceDTO implements Serializable {

    private Long id;

    private String response;

    private Boolean correct;

    private Integer ordering;


    private Long questionId;
    

    private String questionQuestion;

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
    public Boolean getCorrect() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChoiceDTO choiceDTO = (ChoiceDTO) o;

        if ( ! Objects.equals(id, choiceDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChoiceDTO{" +
            "id=" + id +
            ", response='" + response + "'" +
            ", correct='" + correct + "'" +
            ", ordering='" + ordering + "'" +
            '}';
    }
}
