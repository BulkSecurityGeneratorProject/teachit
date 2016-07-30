package com.teachit.service.impl;

import com.teachit.service.DiscursiveAnswerService;
import com.teachit.domain.DiscursiveAnswer;
import com.teachit.repository.DiscursiveAnswerRepository;
import com.teachit.web.rest.dto.DiscursiveAnswerDTO;
import com.teachit.web.rest.mapper.DiscursiveAnswerMapper;
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
 * Service Implementation for managing DiscursiveAnswer.
 */
@Service
@Transactional
public class DiscursiveAnswerServiceImpl implements DiscursiveAnswerService{

    private final Logger log = LoggerFactory.getLogger(DiscursiveAnswerServiceImpl.class);
    
    @Inject
    private DiscursiveAnswerRepository discursiveAnswerRepository;
    
    @Inject
    private DiscursiveAnswerMapper discursiveAnswerMapper;
    
    /**
     * Save a discursiveAnswer.
     * 
     * @param discursiveAnswerDTO the entity to save
     * @return the persisted entity
     */
    public DiscursiveAnswerDTO save(DiscursiveAnswerDTO discursiveAnswerDTO) {
        log.debug("Request to save DiscursiveAnswer : {}", discursiveAnswerDTO);
        DiscursiveAnswer discursiveAnswer = discursiveAnswerMapper.discursiveAnswerDTOToDiscursiveAnswer(discursiveAnswerDTO);
        discursiveAnswer = discursiveAnswerRepository.save(discursiveAnswer);
        DiscursiveAnswerDTO result = discursiveAnswerMapper.discursiveAnswerToDiscursiveAnswerDTO(discursiveAnswer);
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
    public DiscursiveAnswerDTO findOne(Long id) {
        log.debug("Request to get DiscursiveAnswer : {}", id);
        DiscursiveAnswer discursiveAnswer = discursiveAnswerRepository.findOne(id);
        DiscursiveAnswerDTO discursiveAnswerDTO = discursiveAnswerMapper.discursiveAnswerToDiscursiveAnswerDTO(discursiveAnswer);
        return discursiveAnswerDTO;
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
