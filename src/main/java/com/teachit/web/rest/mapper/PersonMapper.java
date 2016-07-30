package com.teachit.web.rest.mapper;

import com.teachit.domain.*;
import com.teachit.web.rest.dto.PersonDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Person and its DTO PersonDTO.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class, })
public interface PersonMapper {

    @Mapping(source = "applicationAdmission.id", target = "applicationAdmissionId")
    PersonDTO personToPersonDTO(Person person);

    List<PersonDTO> peopleToPersonDTOs(List<Person> people);

    @Mapping(source = "applicationAdmissionId", target = "applicationAdmission")
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "teachingCoursees", ignore = true)
    Person personDTOToPerson(PersonDTO personDTO);

    List<Person> personDTOsToPeople(List<PersonDTO> personDTOs);

    default ApplicationAdmission applicationAdmissionFromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationAdmission applicationAdmission = new ApplicationAdmission();
        applicationAdmission.setId(id);
        return applicationAdmission;
    }

    default Course courseFromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
