package com.teachit.service;

import com.teachit.domain.DiscursiveQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing DiscursiveQuestion.
 */
public interface DiscursiveQuestionService {

    /**
     * Save a discursiveQuestion.
     * 
     * @param discursiveQuestion the entity to save
     * @return the persisted entity
     */
    DiscursiveQuestion save(DiscursiveQuestion discursiveQuestion);

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
    DiscursiveQuestion findOne(Long id);

    /**
     *  Delete the "id" discursiveQuestion.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
