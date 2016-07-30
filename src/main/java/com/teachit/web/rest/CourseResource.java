package com.teachit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.teachit.domain.Course;
import com.teachit.service.CourseService;
import com.teachit.web.rest.util.HeaderUtil;
import com.teachit.web.rest.util.PaginationUtil;
import com.teachit.web.rest.dto.CourseDTO;
import com.teachit.web.rest.mapper.CourseMapper;
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
 * REST controller for managing Course.
 */
@RestController
@RequestMapping("/api")
public class CourseResource {

    private final Logger log = LoggerFactory.getLogger(CourseResource.class);
        
    @Inject
    private CourseService courseService;
    
    @Inject
    private CourseMapper courseMapper;
    
    /**
     * POST  /courses : Create a new course.
     *
     * @param courseDTO the courseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courseDTO, or with status 400 (Bad Request) if the course has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/courses",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) throws URISyntaxException {
        log.debug("REST request to save Course : {}", courseDTO);
        if (courseDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("course", "idexists", "A new course cannot already have an ID")).body(null);
        }
        CourseDTO result = courseService.save(courseDTO);
        return ResponseEntity.created(new URI("/api/courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("course", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /courses : Updates an existing course.
     *
     * @param courseDTO the courseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courseDTO,
     * or with status 400 (Bad Request) if the courseDTO is not valid,
     * or with status 500 (Internal Server Error) if the courseDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/courses",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO) throws URISyntaxException {
        log.debug("REST request to update Course : {}", courseDTO);
        if (courseDTO.getId() == null) {
            return createCourse(courseDTO);
        }
        CourseDTO result = courseService.save(courseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("course", courseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /courses : get all the courses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of courses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/courses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CourseDTO>> getAllCourses(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Courses");
        Page<Course> page = courseService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/courses");
        return new ResponseEntity<>(courseMapper.coursesToCourseDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /courses/:id : get the "id" course.
     *
     * @param id the id of the courseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courseDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/courses/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long id) {
        log.debug("REST request to get Course : {}", id);
        CourseDTO courseDTO = courseService.findOne(id);
        return Optional.ofNullable(courseDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /courses/:id : delete the "id" course.
     *
     * @param id the id of the courseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/courses/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.debug("REST request to delete Course : {}", id);
        courseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("course", id.toString())).build();
    }

}
