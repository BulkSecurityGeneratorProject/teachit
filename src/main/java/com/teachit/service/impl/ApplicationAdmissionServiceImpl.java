package com.teachit.service.impl;

import com.teachit.service.ApplicationAdmissionService;
import com.teachit.domain.ApplicationAdmission;
import com.teachit.repository.ApplicationAdmissionRepository;
import com.teachit.web.rest.dto.ApplicationAdmissionDTO;
import com.teachit.web.rest.mapper.ApplicationAdmissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
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
    
    @Inject
    private ApplicationAdmissionMapper applicationAdmissionMapper;
    
    /**
     * Save a applicationAdmission.
     * 
     * @param applicationAdmissionDTO the entity to save
     * @return the persisted entity
     */
    public ApplicationAdmissionDTO save(ApplicationAdmissionDTO applicationAdmissionDTO) {
        log.debug("Request to save ApplicationAdmission : {}", applicationAdmissionDTO);
        ApplicationAdmission applicationAdmission = applicationAdmissionMapper.applicationAdmissionDTOToApplicationAdmission(applicationAdmissionDTO);
        applicationAdmission = applicationAdmissionRepository.save(applicationAdmission);
        ApplicationAdmissionDTO result = applicationAdmissionMapper.applicationAdmissionToApplicationAdmissionDTO(applicationAdmission);
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
    public List<ApplicationAdmissionDTO> findAllWhereCandidateIsNull() {
        log.debug("Request to get all applicationAdmissions where Candidate is null");
        return StreamSupport
            .stream(applicationAdmissionRepository.findAll().spliterator(), false)
            .filter(applicationAdmission -> applicationAdmission.getCandidate() == null)
            .map(applicationAdmissionMapper::applicationAdmissionToApplicationAdmissionDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the applicationAdmissions where Course is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ApplicationAdmissionDTO> findAllWhereCourseIsNull() {
        log.debug("Request to get all applicationAdmissions where Course is null");
        return StreamSupport
            .stream(applicationAdmissionRepository.findAll().spliterator(), false)
            .filter(applicationAdmission -> applicationAdmission.getCourse() == null)
            .map(applicationAdmissionMapper::applicationAdmissionToApplicationAdmissionDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one applicationAdmission by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ApplicationAdmissionDTO findOne(Long id) {
        log.debug("Request to get ApplicationAdmission : {}", id);
        ApplicationAdmission applicationAdmission = applicationAdmissionRepository.findOne(id);
        ApplicationAdmissionDTO applicationAdmissionDTO = applicationAdmissionMapper.applicationAdmissionToApplicationAdmissionDTO(applicationAdmission);
        return applicationAdmissionDTO;
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
