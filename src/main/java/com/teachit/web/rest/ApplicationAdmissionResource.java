package com.teachit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.teachit.domain.ApplicationAdmission;
import com.teachit.repository.ApplicationAdmissionRepository;
import com.teachit.web.rest.util.HeaderUtil;
import com.teachit.web.rest.util.PaginationUtil;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing ApplicationAdmission.
 */
@RestController
@RequestMapping("/api")
public class ApplicationAdmissionResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationAdmissionResource.class);
        
    @Inject
    private ApplicationAdmissionRepository applicationAdmissionRepository;
    
    /**
     * POST  /application-admissions : Create a new applicationAdmission.
     *
     * @param applicationAdmission the applicationAdmission to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationAdmission, or with status 400 (Bad Request) if the applicationAdmission has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/application-admissions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApplicationAdmission> createApplicationAdmission(@RequestBody ApplicationAdmission applicationAdmission) throws URISyntaxException {
        log.debug("REST request to save ApplicationAdmission : {}", applicationAdmission);
        if (applicationAdmission.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("applicationAdmission", "idexists", "A new applicationAdmission cannot already have an ID")).body(null);
        }
        ApplicationAdmission result = applicationAdmissionRepository.save(applicationAdmission);
        return ResponseEntity.created(new URI("/api/application-admissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("applicationAdmission", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /application-admissions : Updates an existing applicationAdmission.
     *
     * @param applicationAdmission the applicationAdmission to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationAdmission,
     * or with status 400 (Bad Request) if the applicationAdmission is not valid,
     * or with status 500 (Internal Server Error) if the applicationAdmission couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/application-admissions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApplicationAdmission> updateApplicationAdmission(@RequestBody ApplicationAdmission applicationAdmission) throws URISyntaxException {
        log.debug("REST request to update ApplicationAdmission : {}", applicationAdmission);
        if (applicationAdmission.getId() == null) {
            return createApplicationAdmission(applicationAdmission);
        }
        ApplicationAdmission result = applicationAdmissionRepository.save(applicationAdmission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("applicationAdmission", applicationAdmission.getId().toString()))
            .body(result);
    }

    /**
     * GET  /application-admissions : get all the applicationAdmissions.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of applicationAdmissions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/application-admissions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ApplicationAdmission>> getAllApplicationAdmissions(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("candidate-is-null".equals(filter)) {
            log.debug("REST request to get all ApplicationAdmissions where candidate is null");
            return new ResponseEntity<>(StreamSupport
                .stream(applicationAdmissionRepository.findAll().spliterator(), false)
                .filter(applicationAdmission -> applicationAdmission.getCandidate() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        if ("course-is-null".equals(filter)) {
            log.debug("REST request to get all ApplicationAdmissions where course is null");
            return new ResponseEntity<>(StreamSupport
                .stream(applicationAdmissionRepository.findAll().spliterator(), false)
                .filter(applicationAdmission -> applicationAdmission.getCourse() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of ApplicationAdmissions");
        Page<ApplicationAdmission> page = applicationAdmissionRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/application-admissions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /application-admissions/:id : get the "id" applicationAdmission.
     *
     * @param id the id of the applicationAdmission to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationAdmission, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/application-admissions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApplicationAdmission> getApplicationAdmission(@PathVariable Long id) {
        log.debug("REST request to get ApplicationAdmission : {}", id);
        ApplicationAdmission applicationAdmission = applicationAdmissionRepository.findOne(id);
        return Optional.ofNullable(applicationAdmission)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /application-admissions/:id : delete the "id" applicationAdmission.
     *
     * @param id the id of the applicationAdmission to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/application-admissions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApplicationAdmission(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationAdmission : {}", id);
        applicationAdmissionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("applicationAdmission", id.toString())).build();
    }

}
