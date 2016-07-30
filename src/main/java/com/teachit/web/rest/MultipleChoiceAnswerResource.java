package com.teachit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.teachit.domain.MultipleChoiceAnswer;
import com.teachit.service.MultipleChoiceAnswerService;
import com.teachit.web.rest.util.HeaderUtil;
import com.teachit.web.rest.util.PaginationUtil;
import com.teachit.web.rest.dto.MultipleChoiceAnswerDTO;
import com.teachit.web.rest.mapper.MultipleChoiceAnswerMapper;
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
 * REST controller for managing MultipleChoiceAnswer.
 */
@RestController
@RequestMapping("/api")
public class MultipleChoiceAnswerResource {

    private final Logger log = LoggerFactory.getLogger(MultipleChoiceAnswerResource.class);
        
    @Inject
    private MultipleChoiceAnswerService multipleChoiceAnswerService;
    
    @Inject
    private MultipleChoiceAnswerMapper multipleChoiceAnswerMapper;
    
    /**
     * POST  /multiple-choice-answers : Create a new multipleChoiceAnswer.
     *
     * @param multipleChoiceAnswerDTO the multipleChoiceAnswerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new multipleChoiceAnswerDTO, or with status 400 (Bad Request) if the multipleChoiceAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/multiple-choice-answers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultipleChoiceAnswerDTO> createMultipleChoiceAnswer(@RequestBody MultipleChoiceAnswerDTO multipleChoiceAnswerDTO) throws URISyntaxException {
        log.debug("REST request to save MultipleChoiceAnswer : {}", multipleChoiceAnswerDTO);
        if (multipleChoiceAnswerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("multipleChoiceAnswer", "idexists", "A new multipleChoiceAnswer cannot already have an ID")).body(null);
        }
        MultipleChoiceAnswerDTO result = multipleChoiceAnswerService.save(multipleChoiceAnswerDTO);
        return ResponseEntity.created(new URI("/api/multiple-choice-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("multipleChoiceAnswer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /multiple-choice-answers : Updates an existing multipleChoiceAnswer.
     *
     * @param multipleChoiceAnswerDTO the multipleChoiceAnswerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated multipleChoiceAnswerDTO,
     * or with status 400 (Bad Request) if the multipleChoiceAnswerDTO is not valid,
     * or with status 500 (Internal Server Error) if the multipleChoiceAnswerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/multiple-choice-answers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultipleChoiceAnswerDTO> updateMultipleChoiceAnswer(@RequestBody MultipleChoiceAnswerDTO multipleChoiceAnswerDTO) throws URISyntaxException {
        log.debug("REST request to update MultipleChoiceAnswer : {}", multipleChoiceAnswerDTO);
        if (multipleChoiceAnswerDTO.getId() == null) {
            return createMultipleChoiceAnswer(multipleChoiceAnswerDTO);
        }
        MultipleChoiceAnswerDTO result = multipleChoiceAnswerService.save(multipleChoiceAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("multipleChoiceAnswer", multipleChoiceAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /multiple-choice-answers : get all the multipleChoiceAnswers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of multipleChoiceAnswers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/multiple-choice-answers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MultipleChoiceAnswerDTO>> getAllMultipleChoiceAnswers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MultipleChoiceAnswers");
        Page<MultipleChoiceAnswer> page = multipleChoiceAnswerService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/multiple-choice-answers");
        return new ResponseEntity<>(multipleChoiceAnswerMapper.multipleChoiceAnswersToMultipleChoiceAnswerDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /multiple-choice-answers/:id : get the "id" multipleChoiceAnswer.
     *
     * @param id the id of the multipleChoiceAnswerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the multipleChoiceAnswerDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/multiple-choice-answers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MultipleChoiceAnswerDTO> getMultipleChoiceAnswer(@PathVariable Long id) {
        log.debug("REST request to get MultipleChoiceAnswer : {}", id);
        MultipleChoiceAnswerDTO multipleChoiceAnswerDTO = multipleChoiceAnswerService.findOne(id);
        return Optional.ofNullable(multipleChoiceAnswerDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /multiple-choice-answers/:id : delete the "id" multipleChoiceAnswer.
     *
     * @param id the id of the multipleChoiceAnswerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/multiple-choice-answers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMultipleChoiceAnswer(@PathVariable Long id) {
        log.debug("REST request to delete MultipleChoiceAnswer : {}", id);
        multipleChoiceAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("multipleChoiceAnswer", id.toString())).build();
    }

}
