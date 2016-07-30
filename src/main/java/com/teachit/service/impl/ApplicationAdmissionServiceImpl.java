package com.teachit.service.impl;

import com.teachit.service.ApplicationAdmissionService;
import com.teachit.domain.ApplicationAdmission;
import com.teachit.repository.ApplicationAdmissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing ApplicationAdmission.
 */
@Service
@Transactional
public class ApplicationAdmissionServiceImpl implements ApplicationAdmissionService{

    private final Logger log = LoggerFactory.getLogger(ApplicationAdmissionServiceImpl.class);
    
    @Inject
    private ApplicationAdmissionRepository applicationAdmissionRepository;
    
    /**
     * Save a applicationAdmission.
     * 
     * @param applicationAdmission the entity to save
     * @return the persisted entity
     */
    public ApplicationAdmission save(ApplicationAdmission applicationAdmission) {
        log.debug("Request to save ApplicationAdmission : {}", applicationAdmission);
        ApplicationAdmission result = applicationAdmissionRepository.save(applicationAdmission);
        return result;
    }

    /**
     *  Get all the applicationAdmissions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ApplicationAdmission> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationAdmissions");
        Page<ApplicationAdmission> result = applicationAdmissionRepository.findAll(pageable); 
        return result;
    }


    /**
     *  get all the applicationAdmissions where Candidate is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ApplicationAdmission> findAllWhereCandidateIsNull() {
        log.debug("Request to get all applicationAdmissions where Candidate is null");
        return StreamSupport
            .stream(applicationAdmissionRepository.findAll().spliterator(), false)
            .filter(applicationAdmission -> applicationAdmission.getCandidate() == null)
            .collect(Collectors.toList());
    }


    /**
     *  get all the applicationAdmissions where Course is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ApplicationAdmission> findAllWhereCourseIsNull() {
        log.debug("Request to get all applicationAdmissions where Course is null");
        return StreamSupport
            .stream(applicationAdmissionRepository.findAll().spliterator(), false)
            .filter(applicationAdmission -> applicationAdmission.getCourse() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one applicationAdmission by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ApplicationAdmission findOne(Long id) {
        log.debug("Request to get ApplicationAdmission : {}", id);
        ApplicationAdmission applicationAdmission = applicationAdmissionRepository.findOne(id);
        return applicationAdmission;
    }

    /**
     *  Delete the  applicationAdmission by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicationAdmission : {}", id);
        applicationAdmissionRepository.delete(id);
    }
}
