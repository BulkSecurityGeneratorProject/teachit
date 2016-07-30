package com.teachit.service.impl;

import com.teachit.service.ContentService;
import com.teachit.domain.Content;
import com.teachit.repository.ContentRepository;
import com.teachit.web.rest.dto.ContentDTO;
import com.teachit.web.rest.mapper.ContentMapper;
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
 * Service Implementation for managing Content.
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService{

    private final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);
    
    @Inject
    private ContentRepository contentRepository;
    
    @Inject
    private ContentMapper contentMapper;
    
    /**
     * Save a content.
     * 
     * @param contentDTO the entity to save
     * @return the persisted entity
     */
    public ContentDTO save(ContentDTO contentDTO) {
        log.debug("Request to save Content : {}", contentDTO);
        Content content = contentMapper.contentDTOToContent(contentDTO);
        content = contentRepository.save(content);
        ContentDTO result = contentMapper.contentToContentDTO(content);
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
    public ContentDTO findOne(Long id) {
        log.debug("Request to get Content : {}", id);
        Content content = contentRepository.findOne(id);
        ContentDTO contentDTO = contentMapper.contentToContentDTO(content);
        return contentDTO;
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
