package com.teachit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.teachit.domain.DiscursiveQuestion;
import com.teachit.repository.DiscursiveQuestionRepository;
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

/**
 * REST controller for managing DiscursiveQuestion.
 */
@RestController
@RequestMapping("/api")
public class DiscursiveQuestionResource {

    private final Logger log = LoggerFactory.getLogger(DiscursiveQuestionResource.class);
        
    @Inject
    private DiscursiveQuestionRepository discursiveQuestionRepository;
    
    /**
     * POST  /discursive-questions : Create a new discursiveQuestion.
     *
     * @param discursiveQuestion the discursiveQuestion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new discursiveQuestion, or with status 400 (Bad Request) if the discursiveQuestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/discursive-questions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DiscursiveQuestion> createDiscursiveQuestion(@RequestBody DiscursiveQuestion discursiveQuestion) throws URISyntaxException {
        log.debug("REST request to save DiscursiveQuestion : {}", discursiveQuestion);
        if (discursiveQuestion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("discursiveQuestion", "idexists", "A new discursiveQuestion cannot already have an ID")).body(null);
        }
        DiscursiveQuestion result = discursiveQuestionRepository.save(discursiveQuestion);
        return ResponseEntity.created(new URI("/api/discursive-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("discursiveQuestion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /discursive-questions : Updates an existing discursiveQuestion.
     *
     * @param discursiveQuestion the discursiveQuestion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated discursiveQuestion,
     * or with status 400 (Bad Request) if the discursiveQuestion is not valid,
     * or with status 500 (Internal Server Error) if the discursiveQuestion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/discursive-questions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DiscursiveQuestion> updateDiscursiveQuestion(@RequestBody DiscursiveQuestion discursiveQuestion) throws URISyntaxException {
        log.debug("REST request to update DiscursiveQuestion : {}", discursiveQuestion);
        if (discursiveQuestion.getId() == null) {
            return createDiscursiveQuestion(discursiveQuestion);
        }
        DiscursiveQuestion result = discursiveQuestionRepository.save(discursiveQuestion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("discursiveQuestion", discursiveQuestion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /discursive-questions : get all the discursiveQuestions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of discursiveQuestions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/discursive-questions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DiscursiveQuestion>> getAllDiscursiveQuestions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DiscursiveQuestions");
        Page<DiscursiveQuestion> page = discursiveQuestionRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/discursive-questions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /discursive-questions/:id : get the "id" discursiveQuestion.
     *
     * @param id the id of the discursiveQuestion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the discursiveQuestion, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/discursive-questions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DiscursiveQuestion> getDiscursiveQuestion(@PathVariable Long id) {
        log.debug("REST request to get DiscursiveQuestion : {}", id);
        DiscursiveQuestion discursiveQuestion = discursiveQuestionRepository.findOne(id);
        return Optional.ofNullable(discursiveQuestion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /discursive-questions/:id : delete the "id" discursiveQuestion.
     *
     * @param id the id of the discursiveQuestion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/discursive-questions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDiscursiveQuestion(@PathVariable Long id) {
        log.debug("REST request to delete DiscursiveQuestion : {}", id);
        discursiveQuestionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("discursiveQuestion", id.toString())).build();
    }

}
