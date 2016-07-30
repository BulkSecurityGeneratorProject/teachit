package com.teachit.service.impl;

import com.teachit.service.MultipleChoiceQuestionService;
import com.teachit.domain.MultipleChoiceQuestion;
import com.teachit.repository.MultipleChoiceQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing MultipleChoiceQuestion.
 */
@Service
@Transactional
public class MultipleChoiceQuestionServiceImpl implements MultipleChoiceQuestionService{

    private final Logger log = LoggerFactory.getLogger(MultipleChoiceQuestionServiceImpl.class);
    
    @Inject
    private MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    
    /**
     * Save a multipleChoiceQuestion.
     * 
     * @param multipleChoiceQuestion the entity to save
     * @return the persisted entity
     */
    public MultipleChoiceQuestion save(MultipleChoiceQuestion multipleChoiceQuestion) {
        log.debug("Request to save MultipleChoiceQuestion : {}", multipleChoiceQuestion);
        MultipleChoiceQuestion result = multipleChoiceQuestionRepository.save(multipleChoiceQuestion);
        return result;
    }

    /**
     *  Get all the multipleChoiceQuestions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<MultipleChoiceQuestion> findAll(Pageable pageable) {
        log.debug("Request to get all MultipleChoiceQuestions");
        Page<MultipleChoiceQuestion> result = multipleChoiceQuestionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one multipleChoiceQuestion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MultipleChoiceQuestion findOne(Long id) {
        log.debug("Request to get MultipleChoiceQuestion : {}", id);
        MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceQuestionRepository.findOne(id);
        return multipleChoiceQuestion;
    }

    /**
     *  Delete the  multipleChoiceQuestion by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MultipleChoiceQuestion : {}", id);
        multipleChoiceQuestionRepository.delete(id);
    }
}
