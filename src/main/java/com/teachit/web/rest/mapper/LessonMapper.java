package com.teachit.web.rest.mapper;

import com.teachit.domain.*;
import com.teachit.web.rest.dto.LessonDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Lesson and its DTO LessonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LessonMapper {

    LessonDTO lessonToLessonDTO(Lesson lesson);

    List<LessonDTO> lessonsToLessonDTOs(List<Lesson> lessons);

    @Mapping(target = "multipleChoices", ignore = true)
    @Mapping(target = "discursiveAnswers", ignore = true)
    @Mapping(target = "contents", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Lesson lessonDTOToLesson(LessonDTO lessonDTO);

    List<Lesson> lessonDTOsToLessons(List<LessonDTO> lessonDTOs);
}
