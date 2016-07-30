package com.teachit.service;

import com.teachit.domain.MultipleChoiceQuestion;
import com.teachit.web.rest.dto.MultipleChoiceQuestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing MultipleChoiceQuestion.
 */
public interface MultipleChoiceQuestionService {

    /**
     * Save a multipleChoiceQuestion.
     * 
     * @param multipleChoiceQuestionDTO the entity to save
     * @return the persisted entity
     */
    MultipleChoiceQuestionDTO save(MultipleChoiceQuestionDTO multipleChoiceQuestionDTO);

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
    MultipleChoiceQuestionDTO findOne(Long id);

    /**
     *  Delete the "id" multipleChoiceQuestion.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
