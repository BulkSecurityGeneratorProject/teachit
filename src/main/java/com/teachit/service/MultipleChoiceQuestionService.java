package com.teachit.service;

import com.teachit.domain.MultipleChoiceQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing MultipleChoiceQuestion.
 */
public interface MultipleChoiceQuestionService {

    /**
     * Save a multipleChoiceQuestion.
     * 
     * @param multipleChoiceQuestion the entity to save
     * @return the persisted entity
     */
    MultipleChoiceQuestion save(MultipleChoiceQuestion multipleChoiceQuestion);

    /**
     *  Get all the multipleChoiceQuestions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MultipleChoiceQuestion> findAll(Pageable pageable);

    /**
     *  Get the "id" multipleChoiceQuestion.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    MultipleChoiceQuestion findOne(Long id);

    /**
     *  Delete the "id" multipleChoiceQuestion.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
