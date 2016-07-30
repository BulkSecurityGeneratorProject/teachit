package com.teachit.service;

import com.teachit.domain.ApplicationAdmission;
import com.teachit.web.rest.dto.ApplicationAdmissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ApplicationAdmission.
 */
public interface ApplicationAdmissionService {

    /**
     * Save a applicationAdmission.
     * 
     * @param applicationAdmissionDTO the entity to save
     * @return the persisted entity
     */
    ApplicationAdmissionDTO save(ApplicationAdmissionDTO applicationAdmissionDTO);

    /**
     *  Get all the applicationAdmissions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ApplicationAdmission> findAll(Pageable pageable);
    /**
     *  Get all the applicationAdmissions where Candidate is null.
     *  
     *  @return the list of entities
     */
    List<ApplicationAdmissionDTO> findAllWhereCandidateIsNull();
    /**
     *  Get all the applicationAdmissions where Course is null.
     *  
     *  @return the list of entities
     */
    List<ApplicationAdmissionDTO> findAllWhereCourseIsNull();

    /**
     *  Get the "id" applicationAdmission.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ApplicationAdmissionDTO findOne(Long id);

    /**
     *  Delete the "id" applicationAdmission.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
