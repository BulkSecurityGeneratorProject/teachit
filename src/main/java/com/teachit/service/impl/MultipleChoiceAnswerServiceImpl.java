package com.teachit.service.impl;

import com.teachit.service.MultipleChoiceAnswerService;
import com.teachit.domain.MultipleChoiceAnswer;
import com.teachit.repository.MultipleChoiceAnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing MultipleChoiceAnswer.
 */
@Service
@Transactional
public class MultipleChoiceAnswerServiceImpl implements MultipleChoiceAnswerService{

    private final Logger log = LoggerFactory.getLogger(MultipleChoiceAnswerServiceImpl.class);
    
    @Inject
    private MultipleChoiceAnswerRepository multipleChoiceAnswerRepository;
    
    /**
     * Save a multipleChoiceAnswer.
     * 
     * @param multipleChoiceAnswer the entity to save
     * @return the persisted entity
     */
    public MultipleChoiceAnswer save(MultipleChoiceAnswer multipleChoiceAnswer) {
        log.debug("Request to save MultipleChoiceAnswer : {}", multipleChoiceAnswer);
        MultipleChoiceAnswer result = multipleChoiceAnswerRepository.save(multipleChoiceAnswer);
        return result;
    }

    /**
     *  Get all the multipleChoiceAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<MultipleChoiceAnswer> findAll(Pageable pageable) {
        log.debug("Request to get all MultipleChoiceAnswers");
        Page<MultipleChoiceAnswer> result = multipleChoiceAnswerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one multipleChoiceAnswer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MultipleChoiceAnswer findOne(Long id) {
        log.debug("Request to get MultipleChoiceAnswer : {}", id);
        MultipleChoiceAnswer multipleChoiceAnswer = multipleChoiceAnswerRepository.findOne(id);
        return multipleChoiceAnswer;
    }

    /**
     *  Delete the  multipleChoiceAnswer by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MultipleChoiceAnswer : {}", id);
        multipleChoiceAnswerRepository.delete(id);
    }
}
