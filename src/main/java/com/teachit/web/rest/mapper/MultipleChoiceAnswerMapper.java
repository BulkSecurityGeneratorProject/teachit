package com.teachit.web.rest.mapper;

import com.teachit.domain.*;
import com.teachit.web.rest.dto.MultipleChoiceAnswerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MultipleChoiceAnswer and its DTO MultipleChoiceAnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MultipleChoiceAnswerMapper {

    @Mapping(source = "choosen.id", target = "choosenId")
    @Mapping(source = "choosen.response", target = "choosenResponse")
    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "question.question", target = "questionQuestion")
    @Mapping(source = "student.id", target = "studentId")
    MultipleChoiceAnswerDTO multipleChoiceAnswerToMultipleChoiceAnswerDTO(MultipleChoiceAnswer multipleChoiceAnswer);

    List<MultipleChoiceAnswerDTO> multipleChoiceAnswersToMultipleChoiceAnswerDTOs(List<MultipleChoiceAnswer> multipleChoiceAnswers);

    @Mapping(source = "choosenId", target = "choosen")
    @Mapping(source = "questionId", target = "question")
    @Mapping(source = "studentId", target = "student")
    MultipleChoiceAnswer multipleChoiceAnswerDTOToMultipleChoiceAnswer(MultipleChoiceAnswerDTO multipleChoiceAnswerDTO);

    List<MultipleChoiceAnswer> multipleChoiceAnswerDTOsToMultipleChoiceAnswers(List<MultipleChoiceAnswerDTO> multipleChoiceAnswerDTOs);

    default Choice choiceFromId(Long id) {
        if (id == null) {
            return null;
        }
        Choice choice = new Choice();
        choice.setId(id);
        return choice;
    }

    default MultipleChoiceQuestion multipleChoiceQuestionFromId(Long id) {
        if (id == null) {
            return null;
        }
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        multipleChoiceQuestion.setId(id);
        return multipleChoiceQuestion;
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
