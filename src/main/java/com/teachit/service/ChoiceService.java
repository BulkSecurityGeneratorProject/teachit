package com.teachit.service;

import com.teachit.domain.Choice;
import com.teachit.web.rest.dto.ChoiceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Choice.
 */
public interface ChoiceService {

    /**
     * Save a choice.
     * 
     * @param choiceDTO the entity to save
     * @return the persisted entity
     */
    ChoiceDTO save(ChoiceDTO choiceDTO);

    /**
     *  Get all the choices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Choice> findAll(Pageable pageable);

    /**
     *  Get the "id" choice.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ChoiceDTO findOne(Long id);

    /**
     *  Delete the "id" choice.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
