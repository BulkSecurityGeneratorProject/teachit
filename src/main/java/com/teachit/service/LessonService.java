package com.teachit.service;

import com.teachit.domain.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Lesson.
 */
public interface LessonService {

    /**
     * Save a lesson.
     * 
     * @param lesson the entity to save
     * @return the persisted entity
     */
    Lesson save(Lesson lesson);

    /**
     *  Get all the lessons.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Lesson> findAll(Pageable pageable);

    /**
     *  Get the "id" lesson.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Lesson findOne(Long id);

    /**
     *  Delete the "id" lesson.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
