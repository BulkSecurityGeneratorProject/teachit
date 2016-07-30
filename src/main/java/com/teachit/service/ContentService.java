package com.teachit.service;

import com.teachit.domain.Content;
import com.teachit.web.rest.dto.ContentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Content.
 */
public interface ContentService {

    /**
     * Save a content.
     * 
     * @param contentDTO the entity to save
     * @return the persisted entity
     */
    ContentDTO save(ContentDTO contentDTO);

    /**
     *  Get all the contents.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Content> findAll(Pageable pageable);

    /**
     *  Get the "id" content.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ContentDTO findOne(Long id);

    /**
     *  Delete the "id" content.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
