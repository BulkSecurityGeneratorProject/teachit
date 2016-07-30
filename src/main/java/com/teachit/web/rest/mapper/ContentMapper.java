package com.teachit.web.rest.mapper;

import com.teachit.domain.*;
import com.teachit.web.rest.dto.ContentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Content and its DTO ContentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContentMapper {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    @Mapping(source = "lesson.id", target = "lessonId")
    @Mapping(source = "lesson.name", target = "lessonName")
    ContentDTO contentToContentDTO(Content content);

    List<ContentDTO> contentsToContentDTOs(List<Content> contents);

    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "lessonId", target = "lesson")
    Content contentDTOToContent(ContentDTO contentDTO);

    List<Content> contentDTOsToContents(List<ContentDTO> contentDTOs);

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
