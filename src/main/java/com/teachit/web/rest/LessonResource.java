package com.teachit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.teachit.domain.Lesson;
import com.teachit.service.LessonService;
import com.teachit.web.rest.util.HeaderUtil;
import com.teachit.web.rest.util.PaginationUtil;
import com.teachit.web.rest.dto.LessonDTO;
import com.teachit.web.rest.mapper.LessonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Lesson.
 */
@RestController
@RequestMapping("/api")
public class LessonResource {

    private final Logger log = LoggerFactory.getLogger(LessonResource.class);
        
    @Inject
    private LessonService lessonService;
    
    @Inject
    private LessonMapper lessonMapper;
    
    /**
     * POST  /lessons : Create a new lesson.
     *
     * @param lessonDTO the lessonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lessonDTO, or with status 400 (Bad Request) if the lesson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lessons",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LessonDTO> createLesson(@RequestBody LessonDTO lessonDTO) throws URISyntaxException {
        log.debug("REST request to save Lesson : {}", lessonDTO);
        if (lessonDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lesson", "idexists", "A new lesson cannot already have an ID")).body(null);
        }
        LessonDTO result = lessonService.save(lessonDTO);
        return ResponseEntity.created(new URI("/api/lessons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lesson", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lessons : Updates an existing lesson.
     *
     * @param lessonDTO the lessonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lessonDTO,
     * or with status 400 (Bad Request) if the lessonDTO is not valid,
     * or with status 500 (Internal Server Error) if the lessonDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lessons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LessonDTO> updateLesson(@RequestBody LessonDTO lessonDTO) throws URISyntaxException {
        log.debug("REST request to update Lesson : {}", lessonDTO);
        if (lessonDTO.getId() == null) {
            return createLesson(lessonDTO);
        }
        LessonDTO result = lessonService.save(lessonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lesson", lessonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lessons : get all the lessons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lessons in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/lessons",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<LessonDTO>> getAllLessons(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Lessons");
        Page<Lesson> page = lessonService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lessons");
        return new ResponseEntity<>(lessonMapper.lessonsToLessonDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /lessons/:id : get the "id" lesson.
     *
     * @param id the id of the lessonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lessonDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/lessons/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LessonDTO> getLesson(@PathVariable Long id) {
        log.debug("REST request to get Lesson : {}", id);
        LessonDTO lessonDTO = lessonService.findOne(id);
        return Optional.ofNullable(lessonDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lessons/:id : delete the "id" lesson.
     *
     * @param id the id of the lessonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/lessons/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        log.debug("REST request to delete Lesson : {}", id);
        lessonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lesson", id.toString())).build();
    }

}
