package com.teachit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.teachit.domain.MultipleChoiceQuestion;
import com.teachit.repository.MultipleChoiceQuestionRepository;
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
 * REST controller for managing MultipleChoiceQuestion.
 */
@RestController
@RequestMapping("/api")
public class MultipleChoiceQuestionResource {

    private final Logger log = LoggerFactory.getLogger(MultipleChoiceQuestionResource.class);
        
    @Inject
    private MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    
    /**
     * POST  /multiple-choice-questions : Create a new multipleChoiceQuestion.
     *
     * @param multipleChoiceQuestion the multipleChoiceQuestion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new multipleChoiceQuestion, or with status 400 (Bad Request) if the multipleChoiceQuestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/multiple-choice-questions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultipleChoiceQuestion> createMultipleChoiceQuestion(@RequestBody MultipleChoiceQuestion multipleChoiceQuestion) throws URISyntaxException {
        log.debug("REST request to save MultipleChoiceQuestion : {}", multipleChoiceQuestion);
        if (multipleChoiceQuestion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("multipleChoiceQuestion", "idexists", "A new multipleChoiceQuestion cannot already have an ID")).body(null);
        }
        MultipleChoiceQuestion result = multipleChoiceQuestionRepository.save(multipleChoiceQuestion);
        return ResponseEntity.created(new URI("/api/multiple-choice-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("multipleChoiceQuestion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /multiple-choice-questions : Updates an existing multipleChoiceQuestion.
     *
     * @param multipleChoiceQuestion the multipleChoiceQuestion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated multipleChoiceQuestion,
     * or with status 400 (Bad Request) if the multipleChoiceQuestion is not valid,
     * or with status 500 (Internal Server Error) if the multipleChoiceQuestion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/multiple-choice-questions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultipleChoiceQuestion> updateMultipleChoiceQuestion(@RequestBody MultipleChoiceQuestion multipleChoiceQuestion) throws URISyntaxException {
        log.debug("REST request to update MultipleChoiceQuestion : {}", multipleChoiceQuestion);
        if (multipleChoiceQuestion.getId() == null) {
            return createMultipleChoiceQuestion(multipleChoiceQuestion);
        }
        MultipleChoiceQuestion result = multipleChoiceQuestionRepository.save(multipleChoiceQuestion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("multipleChoiceQuestion", multipleChoiceQuestion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /multiple-choice-questions : get all the multipleChoiceQuestions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of multipleChoiceQuestions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/multiple-choice-questions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MultipleChoiceQuestion>> getAllMultipleChoiceQuestions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MultipleChoiceQuestions");
        Page<MultipleChoiceQuestion> page = multipleChoiceQuestionRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/multiple-choice-questions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /multiple-choice-questions/:id : get the "id" multipleChoiceQuestion.
     *
     * @param id the id of the multipleChoiceQuestion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the multipleChoiceQuestion, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/multiple-choice-questions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultipleChoiceQuestion> getMultipleChoiceQuestion(@PathVariable Long id) {
        log.debug("REST request to get MultipleChoiceQuestion : {}", id);
        MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceQuestionRepository.findOne(id);
        return Optional.ofNullable(multipleChoiceQuestion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /multiple-choice-questions/:id : delete the "id" multipleChoiceQuestion.
     *
     * @param id the id of the multipleChoiceQuestion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/multiple-choice-questions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMultipleChoiceQuestion(@PathVariable Long id) {
        log.debug("REST request to delete MultipleChoiceQuestion : {}", id);
        multipleChoiceQuestionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("multipleChoiceQuestion", id.toString())).build();
    }

}
