package com.teachit.service;

import com.teachit.domain.MultipleChoiceAnswer;
import com.teachit.web.rest.dto.MultipleChoiceAnswerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing MultipleChoiceAnswer.
 */
public interface MultipleChoiceAnswerService {

    /**
     * Save a multipleChoiceAnswer.
     * 
     * @param multipleChoiceAnswerDTO the entity to save
     * @return the persisted entity
     */
    MultipleChoiceAnswerDTO save(MultipleChoiceAnswerDTO multipleChoiceAnswerDTO);

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
    MultipleChoiceAnswerDTO findOne(Long id);

    /**
     *  Delete the "id" multipleChoiceAnswer.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
