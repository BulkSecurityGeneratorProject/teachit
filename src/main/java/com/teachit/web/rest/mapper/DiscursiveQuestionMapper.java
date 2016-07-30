package com.teachit.web.rest.mapper;

import com.teachit.domain.*;
import com.teachit.web.rest.dto.DiscursiveQuestionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DiscursiveQuestion and its DTO DiscursiveQuestionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DiscursiveQuestionMapper {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    @Mapping(source = "lesson.id", target = "lessonId")
    @Mapping(source = "lesson.name", target = "lessonName")
    DiscursiveQuestionDTO discursiveQuestionToDiscursiveQuestionDTO(DiscursiveQuestion discursiveQuestion);

    List<DiscursiveQuestionDTO> discursiveQuestionsToDiscursiveQuestionDTOs(List<DiscursiveQuestion> discursiveQuestions);

    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "lessonId", target = "lesson")
    DiscursiveQuestion discursiveQuestionDTOToDiscursiveQuestion(DiscursiveQuestionDTO discursiveQuestionDTO);

    List<DiscursiveQuestion> discursiveQuestionDTOsToDiscursiveQuestions(List<DiscursiveQuestionDTO> discursiveQuestionDTOs);

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
