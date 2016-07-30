package com.teachit.service;

import com.teachit.domain.DiscursiveQuestion;
import com.teachit.web.rest.dto.DiscursiveQuestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing DiscursiveQuestion.
 */
public interface DiscursiveQuestionService {

    /**
     * Save a discursiveQuestion.
     * 
     * @param discursiveQuestionDTO the entity to save
     * @return the persisted entity
     */
    DiscursiveQuestionDTO save(DiscursiveQuestionDTO discursiveQuestionDTO);

    /**
     *  Get all the discursiveQuestions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DiscursiveQuestion> findAll(Pageable pageable);

    /**
     *  Get the "id" discursiveQuestion.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    DiscursiveQuestionDTO findOne(Long id);

    /**
     *  Delete the "id" discursiveQuestion.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
