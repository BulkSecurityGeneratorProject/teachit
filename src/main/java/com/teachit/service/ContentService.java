package com.teachit.service;

import com.teachit.domain.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Content.
 */
public interface ContentService {

    /**
     * Save a content.
     * 
     * @param content the entity to save
     * @return the persisted entity
     */
    Content save(Content content);

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
    Content findOne(Long id);

    /**
     *  Delete the "id" content.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
