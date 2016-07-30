package com.teachit.service.impl;

import com.teachit.service.ContentViewService;
import com.teachit.domain.ContentView;
import com.teachit.repository.ContentViewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ContentView.
 */
@Service
@Transactional
public class ContentViewServiceImpl implements ContentViewService{

    private final Logger log = LoggerFactory.getLogger(ContentViewServiceImpl.class);
    
    @Inject
    private ContentViewRepository contentViewRepository;
    
    /**
     * Save a contentView.
     * 
     * @param contentView the entity to save
     * @return the persisted entity
     */
    public ContentView save(ContentView contentView) {
        log.debug("Request to save ContentView : {}", contentView);
        ContentView result = contentViewRepository.save(contentView);
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
    public ContentView findOne(Long id) {
        log.debug("Request to get ContentView : {}", id);
        ContentView contentView = contentViewRepository.findOne(id);
        return contentView;
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
