package com.teachit.service.impl;

import com.teachit.service.MultipleChoiceQuestionService;
import com.teachit.domain.MultipleChoiceQuestion;
import com.teachit.repository.MultipleChoiceQuestionRepository;
import com.teachit.web.rest.dto.MultipleChoiceQuestionDTO;
import com.teachit.web.rest.mapper.MultipleChoiceQuestionMapper;
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
 * Service Implementation for managing MultipleChoiceQuestion.
 */
@Service
@Transactional
public class MultipleChoiceQuestionServiceImpl implements MultipleChoiceQuestionService{

    private final Logger log = LoggerFactory.getLogger(MultipleChoiceQuestionServiceImpl.class);
    
    @Inject
    private MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    
    @Inject
    private MultipleChoiceQuestionMapper multipleChoiceQuestionMapper;
    
    /**
     * Save a multipleChoiceQuestion.
     * 
     * @param multipleChoiceQuestionDTO the entity to save
     * @return the persisted entity
     */
    public MultipleChoiceQuestionDTO save(MultipleChoiceQuestionDTO multipleChoiceQuestionDTO) {
        log.debug("Request to save MultipleChoiceQuestion : {}", multipleChoiceQuestionDTO);
        MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceQuestionMapper.multipleChoiceQuestionDTOToMultipleChoiceQuestion(multipleChoiceQuestionDTO);
        multipleChoiceQuestion = multipleChoiceQuestionRepository.save(multipleChoiceQuestion);
        MultipleChoiceQuestionDTO result = multipleChoiceQuestionMapper.multipleChoiceQuestionToMultipleChoiceQuestionDTO(multipleChoiceQuestion);
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
    public MultipleChoiceQuestionDTO findOne(Long id) {
        log.debug("Request to get MultipleChoiceQuestion : {}", id);
        MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceQuestionRepository.findOne(id);
        MultipleChoiceQuestionDTO multipleChoiceQuestionDTO = multipleChoiceQuestionMapper.multipleChoiceQuestionToMultipleChoiceQuestionDTO(multipleChoiceQuestion);
        return multipleChoiceQuestionDTO;
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
