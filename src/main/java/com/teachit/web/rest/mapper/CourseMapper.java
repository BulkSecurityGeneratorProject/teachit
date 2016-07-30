package com.teachit.web.rest.mapper;

import com.teachit.domain.*;
import com.teachit.web.rest.dto.CourseDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Course and its DTO CourseDTO.
 */
@Mapper(componentModel = "spring", uses = {LessonMapper.class, })
public interface CourseMapper {

    @Mapping(source = "applicationAdmission.id", target = "applicationAdmissionId")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "teacher.name", target = "teacherName")
    CourseDTO courseToCourseDTO(Course course);

    List<CourseDTO> coursesToCourseDTOs(List<Course> courses);

    @Mapping(source = "applicationAdmissionId", target = "applicationAdmission")
    @Mapping(target = "multipleChoices", ignore = true)
    @Mapping(target = "discursiveAnswers", ignore = true)
    @Mapping(target = "contents", ignore = true)
    @Mapping(source = "teacherId", target = "teacher")
    @Mapping(target = "students", ignore = true)
    Course courseDTOToCourse(CourseDTO courseDTO);

    List<Course> courseDTOsToCourses(List<CourseDTO> courseDTOs);

    default ApplicationAdmission applicationAdmissionFromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationAdmission applicationAdmission = new ApplicationAdmission();
        applicationAdmission.setId(id);
        return applicationAdmission;
    }

    default Lesson lessonFromId(Long id) {
        if (id == null) {
            return null;
        }
        Lesson lesson = new Lesson();
        lesson.setId(id);
        return lesson;
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
