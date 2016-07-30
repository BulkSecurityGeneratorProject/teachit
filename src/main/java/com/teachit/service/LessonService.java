package com.teachit.service;

import com.teachit.domain.Lesson;
import com.teachit.web.rest.dto.LessonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Lesson.
 */
public interface LessonService {

    /**
     * Save a lesson.
     * 
     * @param lessonDTO the entity to save
     * @return the persisted entity
     */
    LessonDTO save(LessonDTO lessonDTO);

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
    LessonDTO findOne(Long id);

    /**
     *  Delete the "id" lesson.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
