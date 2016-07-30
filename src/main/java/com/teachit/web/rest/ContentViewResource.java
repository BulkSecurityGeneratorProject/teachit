package com.teachit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.teachit.domain.ContentView;
import com.teachit.repository.ContentViewRepository;
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
 * REST controller for managing ContentView.
 */
@RestController
@RequestMapping("/api")
public class ContentViewResource {

    private final Logger log = LoggerFactory.getLogger(ContentViewResource.class);
        
    @Inject
    private ContentViewRepository contentViewRepository;
    
    /**
     * POST  /content-views : Create a new contentView.
     *
     * @param contentView the contentView to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentView, or with status 400 (Bad Request) if the contentView has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/content-views",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContentView> createContentView(@RequestBody ContentView contentView) throws URISyntaxException {
        log.debug("REST request to save ContentView : {}", contentView);
        if (contentView.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contentView", "idexists", "A new contentView cannot already have an ID")).body(null);
        }
        ContentView result = contentViewRepository.save(contentView);
        return ResponseEntity.created(new URI("/api/content-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contentView", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /content-views : Updates an existing contentView.
     *
     * @param contentView the contentView to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentView,
     * or with status 400 (Bad Request) if the contentView is not valid,
     * or with status 500 (Internal Server Error) if the contentView couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/content-views",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContentView> updateContentView(@RequestBody ContentView contentView) throws URISyntaxException {
        log.debug("REST request to update ContentView : {}", contentView);
        if (contentView.getId() == null) {
            return createContentView(contentView);
        }
        ContentView result = contentViewRepository.save(contentView);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contentView", contentView.getId().toString()))
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
    public ResponseEntity<List<ContentView>> getAllContentViews(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ContentViews");
        Page<ContentView> page = contentViewRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/content-views");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /content-views/:id : get the "id" contentView.
     *
     * @param id the id of the contentView to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentView, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/content-views/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContentView> getContentView(@PathVariable Long id) {
        log.debug("REST request to get ContentView : {}", id);
        ContentView contentView = contentViewRepository.findOne(id);
        return Optional.ofNullable(contentView)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /content-views/:id : delete the "id" contentView.
     *
     * @param id the id of the contentView to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/content-views/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContentView(@PathVariable Long id) {
        log.debug("REST request to delete ContentView : {}", id);
        contentViewRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contentView", id.toString())).build();
    }

}
