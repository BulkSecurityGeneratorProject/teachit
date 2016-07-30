package com.teachit.service.impl;

import com.teachit.service.DiscursiveQuestionService;
import com.teachit.domain.DiscursiveQuestion;
import com.teachit.repository.DiscursiveQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing DiscursiveQuestion.
 */
@Service
@Transactional
public class DiscursiveQuestionServiceImpl implements DiscursiveQuestionService{

    private final Logger log = LoggerFactory.getLogger(DiscursiveQuestionServiceImpl.class);
    
    @Inject
    private DiscursiveQuestionRepository discursiveQuestionRepository;
    
    /**
     * Save a discursiveQuestion.
     * 
     * @param discursiveQuestion the entity to save
     * @return the persisted entity
     */
    public DiscursiveQuestion save(DiscursiveQuestion discursiveQuestion) {
        log.debug("Request to save DiscursiveQuestion : {}", discursiveQuestion);
        DiscursiveQuestion result = discursiveQuestionRepository.save(discursiveQuestion);
        return result;
    }

    /**
     *  Get all the discursiveQuestions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<DiscursiveQuestion> findAll(Pageable pageable) {
        log.debug("Request to get all DiscursiveQuestions");
        Page<DiscursiveQuestion> result = discursiveQuestionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one discursiveQuestion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DiscursiveQuestion findOne(Long id) {
        log.debug("Request to get DiscursiveQuestion : {}", id);
        DiscursiveQuestion discursiveQuestion = discursiveQuestionRepository.findOne(id);
        return discursiveQuestion;
    }

    /**
     *  Delete the  discursiveQuestion by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DiscursiveQuestion : {}", id);
        discursiveQuestionRepository.delete(id);
    }
}
