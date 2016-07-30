package com.teachit.service;

import com.teachit.domain.MultipleChoiceAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing MultipleChoiceAnswer.
 */
public interface MultipleChoiceAnswerService {

    /**
     * Save a multipleChoiceAnswer.
     * 
     * @param multipleChoiceAnswer the entity to save
     * @return the persisted entity
     */
    MultipleChoiceAnswer save(MultipleChoiceAnswer multipleChoiceAnswer);

    /**
     *  Get all the multipleChoiceAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MultipleChoiceAnswer> findAll(Pageable pageable);

    /**
     *  Get the "id" multipleChoiceAnswer.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    MultipleChoiceAnswer findOne(Long id);

    /**
     *  Delete the "id" multipleChoiceAnswer.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
