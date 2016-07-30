package com.teachit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.teachit.domain.ContentView;
import com.teachit.service.ContentViewService;
import com.teachit.web.rest.util.HeaderUtil;
import com.teachit.web.rest.util.PaginationUtil;
import com.teachit.web.rest.dto.ContentViewDTO;
import com.teachit.web.rest.mapper.ContentViewMapper;
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
 * REST controller for managing ContentView.
 */
@RestController
@RequestMapping("/api")
public class ContentViewResource {

    private final Logger log = LoggerFactory.getLogger(ContentViewResource.class);
        
    @Inject
    private ContentViewService contentViewService;
    
    @Inject
    private ContentViewMapper contentViewMapper;
    
    /**
     * POST  /content-views : Create a new contentView.
     *
     * @param contentViewDTO the contentViewDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentViewDTO, or with status 400 (Bad Request) if the contentView has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/content-views",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContentViewDTO> createContentView(@RequestBody ContentViewDTO contentViewDTO) throws URISyntaxException {
        log.debug("REST request to save ContentView : {}", contentViewDTO);
        if (contentViewDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contentView", "idexists", "A new contentView cannot already have an ID")).body(null);
        }
        ContentViewDTO result = contentViewService.save(contentViewDTO);
        return ResponseEntity.created(new URI("/api/content-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contentView", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /content-views : Updates an existing contentView.
     *
     * @param contentViewDTO the contentViewDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentViewDTO,
     * or with status 400 (Bad Request) if the contentViewDTO is not valid,
     * or with status 500 (Internal Server Error) if the contentViewDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/content-views",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContentViewDTO> updateContentView(@RequestBody ContentViewDTO contentViewDTO) throws URISyntaxException {
        log.debug("REST request to update ContentView : {}", contentViewDTO);
        if (contentViewDTO.getId() == null) {
            return createContentView(contentViewDTO);
        }
        ContentViewDTO result = contentViewService.save(contentViewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contentView", contentViewDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /content-views : get all the contentViews.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contentViews in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/content-views",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ContentViewDTO>> getAllContentViews(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ContentViews");
        Page<ContentView> page = contentViewService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/content-views");
        return new ResponseEntity<>(contentViewMapper.contentViewsToContentViewDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /content-views/:id : get the "id" contentView.
     *
     * @param id the id of the contentViewDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentViewDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/content-views/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContentViewDTO> getContentView(@PathVariable Long id) {
        log.debug("REST request to get ContentView : {}", id);
        ContentViewDTO contentViewDTO = contentViewService.findOne(id);
        return Optional.ofNullable(contentViewDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /content-views/:id : delete the "id" contentView.
     *
     * @param id the id of the contentViewDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/content-views/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContentView(@PathVariable Long id) {
        log.debug("REST request to delete ContentView : {}", id);
        contentViewService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contentView", id.toString())).build();
    }

}
