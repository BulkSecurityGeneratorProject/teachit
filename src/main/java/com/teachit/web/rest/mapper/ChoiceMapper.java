package com.teachit.web.rest.mapper;

import com.teachit.domain.*;
import com.teachit.web.rest.dto.ChoiceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Choice and its DTO ChoiceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChoiceMapper {

    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "question.question", target = "questionQuestion")
    ChoiceDTO choiceToChoiceDTO(Choice choice);

    List<ChoiceDTO> choicesToChoiceDTOs(List<Choice> choices);

    @Mapping(source = "questionId", target = "question")
    Choice choiceDTOToChoice(ChoiceDTO choiceDTO);

    List<Choice> choiceDTOsToChoices(List<ChoiceDTO> choiceDTOs);

    default MultipleChoiceQuestion multipleChoiceQuestionFromId(Long id) {
        if (id == null) {
            return null;
        }
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        multipleChoiceQuestion.setId(id);
        return multipleChoiceQuestion;
    }
}
