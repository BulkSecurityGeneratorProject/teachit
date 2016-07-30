package com.teachit.service.impl;

import com.teachit.service.ContentService;
import com.teachit.domain.Content;
import com.teachit.repository.ContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Content.
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService{

    private final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);
    
    @Inject
    private ContentRepository contentRepository;
    
    /**
     * Save a content.
     * 
     * @param content the entity to save
     * @return the persisted entity
     */
    public Content save(Content content) {
        log.debug("Request to save Content : {}", content);
        Content result = contentRepository.save(content);
        return result;
    }

    /**
     *  Get all the contents.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Content> findAll(Pageable pageable) {
        log.debug("Request to get all Contents");
        Page<Content> result = contentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one content by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Content findOne(Long id) {
        log.debug("Request to get Content : {}", id);
        Content content = contentRepository.findOne(id);
        return content;
    }

    /**
     *  Delete the  content by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Content : {}", id);
        contentRepository.delete(id);
    }
}
