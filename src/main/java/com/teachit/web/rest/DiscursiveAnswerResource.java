package com.teachit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.teachit.domain.DiscursiveAnswer;
import com.teachit.repository.DiscursiveAnswerRepository;
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
 * REST controller for managing DiscursiveAnswer.
 */
@RestController
@RequestMapping("/api")
public class DiscursiveAnswerResource {

    private final Logger log = LoggerFactory.getLogger(DiscursiveAnswerResource.class);
        
    @Inject
    private DiscursiveAnswerRepository discursiveAnswerRepository;
    
    /**
     * POST  /discursive-answers : Create a new discursiveAnswer.
     *
     * @param discursiveAnswer the discursiveAnswer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new discursiveAnswer, or with status 400 (Bad Request) if the discursiveAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/discursive-answers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DiscursiveAnswer> createDiscursiveAnswer(@RequestBody DiscursiveAnswer discursiveAnswer) throws URISyntaxException {
        log.debug("REST request to save DiscursiveAnswer : {}", discursiveAnswer);
        if (discursiveAnswer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("discursiveAnswer", "idexists", "A new discursiveAnswer cannot already have an ID")).body(null);
        }
        DiscursiveAnswer result = discursiveAnswerRepository.save(discursiveAnswer);
        return ResponseEntity.created(new URI("/api/discursive-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("discursiveAnswer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /discursive-answers : Updates an existing discursiveAnswer.
     *
     * @param discursiveAnswer the discursiveAnswer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated discursiveAnswer,
     * or with status 400 (Bad Request) if the discursiveAnswer is not valid,
     * or with status 500 (Internal Server Error) if the discursiveAnswer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/discursive-answers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DiscursiveAnswer> updateDiscursiveAnswer(@RequestBody DiscursiveAnswer discursiveAnswer) throws URISyntaxException {
        log.debug("REST request to update DiscursiveAnswer : {}", discursiveAnswer);
        if (discursiveAnswer.getId() == null) {
            return createDiscursiveAnswer(discursiveAnswer);
        }
        DiscursiveAnswer result = discursiveAnswerRepository.save(discursiveAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("discursiveAnswer", discursiveAnswer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /discursive-answers : get all the discursiveAnswers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of discursiveAnswers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/discursive-answers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DiscursiveAnswer>> getAllDiscursiveAnswers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DiscursiveAnswers");
        Page<DiscursiveAnswer> page = discursiveAnswerRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/discursive-answers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /discursive-answers/:id : get the "id" discursiveAnswer.
     *
     * @param id the id of the discursiveAnswer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the discursiveAnswer, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/discursive-answers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DiscursiveAnswer> getDiscursiveAnswer(@PathVariable Long id) {
        log.debug("REST request to get DiscursiveAnswer : {}", id);
        DiscursiveAnswer discursiveAnswer = discursiveAnswerRepository.findOne(id);
        return Optional.ofNullable(discursiveAnswer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /discursive-answers/:id : delete the "id" discursiveAnswer.
     *
     * @param id the id of the discursiveAnswer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/discursive-answers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDiscursiveAnswer(@PathVariable Long id) {
        log.debug("REST request to delete DiscursiveAnswer : {}", id);
        discursiveAnswerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("discursiveAnswer", id.toString())).build();
    }

}
