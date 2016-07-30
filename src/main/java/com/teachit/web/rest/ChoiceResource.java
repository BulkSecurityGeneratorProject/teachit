package com.teachit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.teachit.domain.Choice;
import com.teachit.service.ChoiceService;
import com.teachit.web.rest.util.HeaderUtil;
import com.teachit.web.rest.util.PaginationUtil;
import com.teachit.web.rest.dto.ChoiceDTO;
import com.teachit.web.rest.mapper.ChoiceMapper;
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
 * REST controller for managing Choice.
 */
@RestController
@RequestMapping("/api")
public class ChoiceResource {

    private final Logger log = LoggerFactory.getLogger(ChoiceResource.class);
        
    @Inject
    private ChoiceService choiceService;
    
    @Inject
    private ChoiceMapper choiceMapper;
    
    /**
     * POST  /choices : Create a new choice.
     *
     * @param choiceDTO the choiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new choiceDTO, or with status 400 (Bad Request) if the choice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/choices",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ChoiceDTO> createChoice(@RequestBody ChoiceDTO choiceDTO) throws URISyntaxException {
        log.debug("REST request to save Choice : {}", choiceDTO);
        if (choiceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("choice", "idexists", "A new choice cannot already have an ID")).body(null);
        }
        ChoiceDTO result = choiceService.save(choiceDTO);
        return ResponseEntity.created(new URI("/api/choices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("choice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /choices : Updates an existing choice.
     *
     * @param choiceDTO the choiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated choiceDTO,
     * or with status 400 (Bad Request) if the choiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the choiceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/choices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ChoiceDTO> updateChoice(@RequestBody ChoiceDTO choiceDTO) throws URISyntaxException {
        log.debug("REST request to update Choice : {}", choiceDTO);
        if (choiceDTO.getId() == null) {
            return createChoice(choiceDTO);
        }
        ChoiceDTO result = choiceService.save(choiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("choice", choiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /choices : get all the choices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of choices in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/choices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ChoiceDTO>> getAllChoices(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Choices");
        Page<Choice> page = choiceService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/choices");
        return new ResponseEntity<>(choiceMapper.choicesToChoiceDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /choices/:id : get the "id" choice.
     *
     * @param id the id of the choiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the choiceDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/choices/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ChoiceDTO> getChoice(@PathVariable Long id) {
        log.debug("REST request to get Choice : {}", id);
        ChoiceDTO choiceDTO = choiceService.findOne(id);
        return Optional.ofNullable(choiceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /choices/:id : delete the "id" choice.
     *
     * @param id the id of the choiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/choices/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteChoice(@PathVariable Long id) {
        log.debug("REST request to delete Choice : {}", id);
        choiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("choice", id.toString())).build();
    }

}
