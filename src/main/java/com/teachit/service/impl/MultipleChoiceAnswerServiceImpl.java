package com.teachit.service.impl;

import com.teachit.service.MultipleChoiceAnswerService;
import com.teachit.domain.MultipleChoiceAnswer;
import com.teachit.repository.MultipleChoiceAnswerRepository;
import com.teachit.web.rest.dto.MultipleChoiceAnswerDTO;
import com.teachit.web.rest.mapper.MultipleChoiceAnswerMapper;
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
 * Service Implementation for managing MultipleChoiceAnswer.
 */
@Service
@Transactional
public class MultipleChoiceAnswerServiceImpl implements MultipleChoiceAnswerService{

    private final Logger log = LoggerFactory.getLogger(MultipleChoiceAnswerServiceImpl.class);
    
    @Inject
    private MultipleChoiceAnswerRepository multipleChoiceAnswerRepository;
    
    @Inject
    private MultipleChoiceAnswerMapper multipleChoiceAnswerMapper;
    
    /**
     * Save a multipleChoiceAnswer.
     * 
     * @param multipleChoiceAnswerDTO the entity to save
     * @return the persisted entity
     */
    public MultipleChoiceAnswerDTO save(MultipleChoiceAnswerDTO multipleChoiceAnswerDTO) {
        log.debug("Request to save MultipleChoiceAnswer : {}", multipleChoiceAnswerDTO);
        MultipleChoiceAnswer multipleChoiceAnswer = multipleChoiceAnswerMapper.multipleChoiceAnswerDTOToMultipleChoiceAnswer(multipleChoiceAnswerDTO);
        multipleChoiceAnswer = multipleChoiceAnswerRepository.save(multipleChoiceAnswer);
        MultipleChoiceAnswerDTO result = multipleChoiceAnswerMapper.multipleChoiceAnswerToMultipleChoiceAnswerDTO(multipleChoiceAnswer);
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
    public MultipleChoiceAnswerDTO findOne(Long id) {
        log.debug("Request to get MultipleChoiceAnswer : {}", id);
        MultipleChoiceAnswer multipleChoiceAnswer = multipleChoiceAnswerRepository.findOne(id);
        MultipleChoiceAnswerDTO multipleChoiceAnswerDTO = multipleChoiceAnswerMapper.multipleChoiceAnswerToMultipleChoiceAnswerDTO(multipleChoiceAnswer);
        return multipleChoiceAnswerDTO;
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
