package com.teachit.service.impl;

import com.teachit.service.ChoiceService;
import com.teachit.domain.Choice;
import com.teachit.repository.ChoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Choice.
 */
@Service
@Transactional
public class ChoiceServiceImpl implements ChoiceService{

    private final Logger log = LoggerFactory.getLogger(ChoiceServiceImpl.class);
    
    @Inject
    private ChoiceRepository choiceRepository;
    
    /**
     * Save a choice.
     * 
     * @param choice the entity to save
     * @return the persisted entity
     */
    public Choice save(Choice choice) {
        log.debug("Request to save Choice : {}", choice);
        Choice result = choiceRepository.save(choice);
        return result;
    }

    /**
     *  Get all the choices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Choice> findAll(Pageable pageable) {
        log.debug("Request to get all Choices");
        Page<Choice> result = choiceRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one choice by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Choice findOne(Long id) {
        log.debug("Request to get Choice : {}", id);
        Choice choice = choiceRepository.findOne(id);
        return choice;
    }

    /**
     *  Delete the  choice by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Choice : {}", id);
        choiceRepository.delete(id);
    }
}
