package com.teachit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.teachit.domain.MultipleChoiceQuestion;
import com.teachit.service.MultipleChoiceQuestionService;
import com.teachit.web.rest.util.HeaderUtil;
import com.teachit.web.rest.util.PaginationUtil;
import com.teachit.web.rest.dto.MultipleChoiceQuestionDTO;
import com.teachit.web.rest.mapper.MultipleChoiceQuestionMapper;
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
 * REST controller for managing MultipleChoiceQuestion.
 */
@RestController
@RequestMapping("/api")
public class MultipleChoiceQuestionResource {

    private final Logger log = LoggerFactory.getLogger(MultipleChoiceQuestionResource.class);
        
    @Inject
    private MultipleChoiceQuestionService multipleChoiceQuestionService;
    
    @Inject
    private MultipleChoiceQuestionMapper multipleChoiceQuestionMapper;
    
    /**
     * POST  /multiple-choice-questions : Create a new multipleChoiceQuestion.
     *
     * @param multipleChoiceQuestionDTO the multipleChoiceQuestionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new multipleChoiceQuestionDTO, or with status 400 (Bad Request) if the multipleChoiceQuestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/multiple-choice-questions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultipleChoiceQuestionDTO> createMultipleChoiceQuestion(@RequestBody MultipleChoiceQuestionDTO multipleChoiceQuestionDTO) throws URISyntaxException {
        log.debug("REST request to save MultipleChoiceQuestion : {}", multipleChoiceQuestionDTO);
        if (multipleChoiceQuestionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("multipleChoiceQuestion", "idexists", "A new multipleChoiceQuestion cannot already have an ID")).body(null);
        }
        MultipleChoiceQuestionDTO result = multipleChoiceQuestionService.save(multipleChoiceQuestionDTO);
        return ResponseEntity.created(new URI("/api/multiple-choice-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("multipleChoiceQuestion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /multiple-choice-questions : Updates an existing multipleChoiceQuestion.
     *
     * @param multipleChoiceQuestionDTO the multipleChoiceQuestionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated multipleChoiceQuestionDTO,
     * or with status 400 (Bad Request) if the multipleChoiceQuestionDTO is not valid,
     * or with status 500 (Internal Server Error) if the multipleChoiceQuestionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/multiple-choice-questions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultipleChoiceQuestionDTO> updateMultipleChoiceQuestion(@RequestBody MultipleChoiceQuestionDTO multipleChoiceQuestionDTO) throws URISyntaxException {
        log.debug("REST request to update MultipleChoiceQuestion : {}", multipleChoiceQuestionDTO);
        if (multipleChoiceQuestionDTO.getId() == null) {
            return createMultipleChoiceQuestion(multipleChoiceQuestionDTO);
        }
        MultipleChoiceQuestionDTO result = multipleChoiceQuestionService.save(multipleChoiceQuestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("multipleChoiceQuestion", multipleChoiceQuestionDTO.getId().toString()))
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
    public ResponseEntity<List<MultipleChoiceQuestionDTO>> getAllMultipleChoiceQuestions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MultipleChoiceQuestions");
        Page<MultipleChoiceQuestion> page = multipleChoiceQuestionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/multiple-choice-questions");
        return new ResponseEntity<>(multipleChoiceQuestionMapper.multipleChoiceQuestionsToMultipleChoiceQuestionDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /multiple-choice-questions/:id : get the "id" multipleChoiceQuestion.
     *
     * @param id the id of the multipleChoiceQuestionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the multipleChoiceQuestionDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/multiple-choice-questions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultipleChoiceQuestionDTO> getMultipleChoiceQuestion(@PathVariable Long id) {
        log.debug("REST request to get MultipleChoiceQuestion : {}", id);
        MultipleChoiceQuestionDTO multipleChoiceQuestionDTO = multipleChoiceQuestionService.findOne(id);
        return Optional.ofNullable(multipleChoiceQuestionDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /multiple-choice-questions/:id : delete the "id" multipleChoiceQuestion.
     *
     * @param id the id of the multipleChoiceQuestionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/multiple-choice-questions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMultipleChoiceQuestion(@PathVariable Long id) {
        log.debug("REST request to delete MultipleChoiceQuestion : {}", id);
        multipleChoiceQuestionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("multipleChoiceQuestion", id.toString())).build();
    }

}
