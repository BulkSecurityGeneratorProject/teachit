package com.teachit.service.impl;

import com.teachit.service.ContentViewService;
import com.teachit.domain.ContentView;
import com.teachit.repository.ContentViewRepository;
import com.teachit.web.rest.dto.ContentViewDTO;
import com.teachit.web.rest.mapper.ContentViewMapper;
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
 * Service Implementation for managing ContentView.
 */
@Service
@Transactional
public class ContentViewServiceImpl implements ContentViewService{

    private final Logger log = LoggerFactory.getLogger(ContentViewServiceImpl.class);
    
    @Inject
    private ContentViewRepository contentViewRepository;
    
    @Inject
    private ContentViewMapper contentViewMapper;
    
    /**
     * Save a contentView.
     * 
     * @param contentViewDTO the entity to save
     * @return the persisted entity
     */
    public ContentViewDTO save(ContentViewDTO contentViewDTO) {
        log.debug("Request to save ContentView : {}", contentViewDTO);
        ContentView contentView = contentViewMapper.contentViewDTOToContentView(contentViewDTO);
        contentView = contentViewRepository.save(contentView);
        ContentViewDTO result = contentViewMapper.contentViewToContentViewDTO(contentView);
        return result;
    }

    /**
     *  Get all the contentViews.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ContentView> findAll(Pageable pageable) {
        log.debug("Request to get all ContentViews");
        Page<ContentView> result = contentViewRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one contentView by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ContentViewDTO findOne(Long id) {
        log.debug("Request to get ContentView : {}", id);
        ContentView contentView = contentViewRepository.findOne(id);
        ContentViewDTO contentViewDTO = contentViewMapper.contentViewToContentViewDTO(contentView);
        return contentViewDTO;
    }

    /**
     *  Delete the  contentView by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ContentView : {}", id);
        contentViewRepository.delete(id);
    }
}
