package com.teachit.service.impl;

import com.teachit.service.DiscursiveAnswerService;
import com.teachit.domain.DiscursiveAnswer;
import com.teachit.repository.DiscursiveAnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing DiscursiveAnswer.
 */
@Service
@Transactional
public class DiscursiveAnswerServiceImpl implements DiscursiveAnswerService{

    private final Logger log = LoggerFactory.getLogger(DiscursiveAnswerServiceImpl.class);
    
    @Inject
    private DiscursiveAnswerRepository discursiveAnswerRepository;
    
    /**
     * Save a discursiveAnswer.
     * 
     * @param discursiveAnswer the entity to save
     * @return the persisted entity
     */
    public DiscursiveAnswer save(DiscursiveAnswer discursiveAnswer) {
        log.debug("Request to save DiscursiveAnswer : {}", discursiveAnswer);
        DiscursiveAnswer result = discursiveAnswerRepository.save(discursiveAnswer);
        return result;
    }

    /**
     *  Get all the discursiveAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<DiscursiveAnswer> findAll(Pageable pageable) {
        log.debug("Request to get all DiscursiveAnswers");
        Page<DiscursiveAnswer> result = discursiveAnswerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one discursiveAnswer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DiscursiveAnswer findOne(Long id) {
        log.debug("Request to get DiscursiveAnswer : {}", id);
        DiscursiveAnswer discursiveAnswer = discursiveAnswerRepository.findOne(id);
        return discursiveAnswer;
    }

    /**
     *  Delete the  discursiveAnswer by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DiscursiveAnswer : {}", id);
        discursiveAnswerRepository.delete(id);
    }
}
