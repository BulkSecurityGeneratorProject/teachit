package com.teachit.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the DiscursiveAnswer entity.
 */
public class DiscursiveAnswerDTO implements Serializable {

    private Long id;

    private String answer;


    private Long questionId;
    

    private String questionQuestion;

    private Long studentId;
    
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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long discursiveQuestionId) {
        this.questionId = discursiveQuestionId;
    }


    public String getQuestionQuestion() {
        return questionQuestion;
    }

    public void setQuestionQuestion(String discursiveQuestionQuestion) {
        this.questionQuestion = discursiveQuestionQuestion;
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

        DiscursiveAnswerDTO discursiveAnswerDTO = (DiscursiveAnswerDTO) o;

        if ( ! Objects.equals(id, discursiveAnswerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DiscursiveAnswerDTO{" +
            "id=" + id +
            ", answer='" + answer + "'" +
            '}';
    }
}
