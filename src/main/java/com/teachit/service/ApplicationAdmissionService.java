package com.teachit.service;

import com.teachit.domain.ApplicationAdmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ApplicationAdmission.
 */
public interface ApplicationAdmissionService {

    /**
     * Save a applicationAdmission.
     * 
     * @param applicationAdmission the entity to save
     * @return the persisted entity
     */
    ApplicationAdmission save(ApplicationAdmission applicationAdmission);

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
    List<ApplicationAdmission> findAllWhereCandidateIsNull();
    /**
     *  Get all the applicationAdmissions where Course is null.
     *  
     *  @return the list of entities
     */
    List<ApplicationAdmission> findAllWhereCourseIsNull();

    /**
     *  Get the "id" applicationAdmission.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ApplicationAdmission findOne(Long id);

    /**
     *  Delete the "id" applicationAdmission.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
