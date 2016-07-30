package com.teachit.service.impl;

import com.teachit.service.LessonService;
import com.teachit.domain.Lesson;
import com.teachit.repository.LessonRepository;
import com.teachit.web.rest.dto.LessonDTO;
import com.teachit.web.rest.mapper.LessonMapper;
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
 * Service Implementation for managing Lesson.
 */
@Service
@Transactional
public class LessonServiceImpl implements LessonService{

    private final Logger log = LoggerFactory.getLogger(LessonServiceImpl.class);
    
    @Inject
    private LessonRepository lessonRepository;
    
    @Inject
    private LessonMapper lessonMapper;
    
    /**
     * Save a lesson.
     * 
     * @param lessonDTO the entity to save
     * @return the persisted entity
     */
    public LessonDTO save(LessonDTO lessonDTO) {
        log.debug("Request to save Lesson : {}", lessonDTO);
        Lesson lesson = lessonMapper.lessonDTOToLesson(lessonDTO);
        lesson = lessonRepository.save(lesson);
        LessonDTO result = lessonMapper.lessonToLessonDTO(lesson);
        return result;
    }

    /**
     *  Get all the lessons.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Lesson> findAll(Pageable pageable) {
        log.debug("Request to get all Lessons");
        Page<Lesson> result = lessonRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one lesson by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LessonDTO findOne(Long id) {
        log.debug("Request to get Lesson : {}", id);
        Lesson lesson = lessonRepository.findOne(id);
        LessonDTO lessonDTO = lessonMapper.lessonToLessonDTO(lesson);
        return lessonDTO;
    }

    /**
     *  Delete the  lesson by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Lesson : {}", id);
        lessonRepository.delete(id);
    }
}
