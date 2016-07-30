package com.teachit.web.rest.mapper;

import com.teachit.domain.*;
import com.teachit.web.rest.dto.ApplicationAdmissionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ApplicationAdmission and its DTO ApplicationAdmissionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationAdmissionMapper {

    ApplicationAdmissionDTO applicationAdmissionToApplicationAdmissionDTO(ApplicationAdmission applicationAdmission);

    List<ApplicationAdmissionDTO> applicationAdmissionsToApplicationAdmissionDTOs(List<ApplicationAdmission> applicationAdmissions);

    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "course", ignore = true)
    ApplicationAdmission applicationAdmissionDTOToApplicationAdmission(ApplicationAdmissionDTO applicationAdmissionDTO);

    List<ApplicationAdmission> applicationAdmissionDTOsToApplicationAdmissions(List<ApplicationAdmissionDTO> applicationAdmissionDTOs);
}
