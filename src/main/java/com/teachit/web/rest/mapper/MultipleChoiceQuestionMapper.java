package com.teachit.web.rest.mapper;

import com.teachit.domain.*;
import com.teachit.web.rest.dto.MultipleChoiceQuestionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MultipleChoiceQuestion and its DTO MultipleChoiceQuestionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MultipleChoiceQuestionMapper {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    @Mapping(source = "lesson.id", target = "lessonId")
    @Mapping(source = "lesson.name", target = "lessonName")
    MultipleChoiceQuestionDTO multipleChoiceQuestionToMultipleChoiceQuestionDTO(MultipleChoiceQuestion multipleChoiceQuestion);

    List<MultipleChoiceQuestionDTO> multipleChoiceQuestionsToMultipleChoiceQuestionDTOs(List<MultipleChoiceQuestion> multipleChoiceQuestions);

    @Mapping(target = "answers", ignore = true)
    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "lessonId", target = "lesson")
    MultipleChoiceQuestion multipleChoiceQuestionDTOToMultipleChoiceQuestion(MultipleChoiceQuestionDTO multipleChoiceQuestionDTO);

    List<MultipleChoiceQuestion> multipleChoiceQuestionDTOsToMultipleChoiceQuestions(List<MultipleChoiceQuestionDTO> multipleChoiceQuestionDTOs);

    default Course courseFromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }

    default Lesson lessonFromId(Long id) {
        if (id == null) {
            return null;
        }
        Lesson lesson = new Lesson();
        lesson.setId(id);
        return lesson;
    }
}
