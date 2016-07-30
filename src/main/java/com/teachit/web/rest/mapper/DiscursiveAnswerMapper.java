package com.teachit.web.rest.mapper;

import com.teachit.domain.*;
import com.teachit.web.rest.dto.DiscursiveAnswerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DiscursiveAnswer and its DTO DiscursiveAnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DiscursiveAnswerMapper {

    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "question.question", target = "questionQuestion")
    @Mapping(source = "student.id", target = "studentId")
    DiscursiveAnswerDTO discursiveAnswerToDiscursiveAnswerDTO(DiscursiveAnswer discursiveAnswer);

    List<DiscursiveAnswerDTO> discursiveAnswersToDiscursiveAnswerDTOs(List<DiscursiveAnswer> discursiveAnswers);

    @Mapping(source = "questionId", target = "question")
    @Mapping(source = "studentId", target = "student")
    DiscursiveAnswer discursiveAnswerDTOToDiscursiveAnswer(DiscursiveAnswerDTO discursiveAnswerDTO);

    List<DiscursiveAnswer> discursiveAnswerDTOsToDiscursiveAnswers(List<DiscursiveAnswerDTO> discursiveAnswerDTOs);

    default DiscursiveQuestion discursiveQuestionFromId(Long id) {
        if (id == null) {
            return null;
        }
        DiscursiveQuestion discursiveQuestion = new DiscursiveQuestion();
        discursiveQuestion.setId(id);
        return discursiveQuestion;
    }

    default Person personFromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
