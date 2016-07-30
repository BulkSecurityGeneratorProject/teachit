package com.teachit.service.impl;

import com.teachit.service.DiscursiveQuestionService;
import com.teachit.domain.DiscursiveQuestion;
import com.teachit.repository.DiscursiveQuestionRepository;
import com.teachit.web.rest.dto.DiscursiveQuestionDTO;
import com.teachit.web.rest.mapper.DiscursiveQuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DiscursiveQuestion.
 */
@Service
@Transactional
public class DiscursiveQuestionServiceImpl implements DiscursiveQuestionService{

    private final Logger log = LoggerFactory.getLogger(DiscursiveQuestionServiceImpl.class);
    
    @Inject
    private DiscursiveQuestionRepository discursiveQuestionRepository;
    
    @Inject
    private DiscursiveQuestionMapper discursiveQuestionMapper;
    
    /**
     * Save a discursiveQuestion.
     * 
     * @param discursiveQuestionDTO the entity to save
     * @return the persisted entity
     */
    public DiscursiveQuestionDTO save(DiscursiveQuestionDTO discursiveQuestionDTO) {
        log.debug("Request to save DiscursiveQuestion : {}", discursiveQuestionDTO);
        DiscursiveQuestion discursiveQuestion = discursiveQuestionMapper.discursiveQuestionDTOToDiscursiveQuestion(discursiveQuestionDTO);
        discursiveQuestion = discursiveQuestionRepository.save(discursiveQuestion);
        DiscursiveQuestionDTO result = discursiveQuestionMapper.discursiveQuestionToDiscursiveQuestionDTO(discursiveQuestion);
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
    public DiscursiveQuestionDTO findOne(Long id) {
        log.debug("Request to get DiscursiveQuestion : {}", id);
        DiscursiveQuestion discursiveQuestion = discursiveQuestionRepository.findOne(id);
        DiscursiveQuestionDTO discursiveQuestionDTO = discursiveQuestionMapper.discursiveQuestionToDiscursiveQuestionDTO(discursiveQuestion);
        return discursiveQuestionDTO;
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
