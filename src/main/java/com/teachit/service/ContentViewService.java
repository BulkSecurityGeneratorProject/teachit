package com.teachit.service;

import com.teachit.domain.ContentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ContentView.
 */
public interface ContentViewService {

    /**
     * Save a contentView.
     * 
     * @param contentView the entity to save
     * @return the persisted entity
     */
    ContentView save(ContentView contentView);

    /**
     *  Get all the contentViews.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContentView> findAll(Pageable pageable);

    /**
     *  Get the "id" contentView.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ContentView findOne(Long id);

    /**
     *  Delete the "id" contentView.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
