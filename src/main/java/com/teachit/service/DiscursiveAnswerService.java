package com.teachit.service;

import com.teachit.domain.DiscursiveAnswer;
import com.teachit.web.rest.dto.DiscursiveAnswerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing DiscursiveAnswer.
 */
public interface DiscursiveAnswerService {

    /**
     * Save a discursiveAnswer.
     * 
     * @param discursiveAnswerDTO the entity to save
     * @return the persisted entity
     */
    DiscursiveAnswerDTO save(DiscursiveAnswerDTO discursiveAnswerDTO);

    /**
     *  Get all the discursiveAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DiscursiveAnswer> findAll(Pageable pageable);

    /**
     *  Get the "id" discursiveAnswer.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    DiscursiveAnswerDTO findOne(Long id);

    /**
     *  Delete the "id" discursiveAnswer.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
