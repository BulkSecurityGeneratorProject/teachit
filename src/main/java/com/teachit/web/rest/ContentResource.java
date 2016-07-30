package com.teachit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.teachit.domain.Content;
import com.teachit.service.ContentService;
import com.teachit.web.rest.util.HeaderUtil;
import com.teachit.web.rest.util.PaginationUtil;
import com.teachit.web.rest.dto.ContentDTO;
import com.teachit.web.rest.mapper.ContentMapper;
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
 * REST controller for managing Content.
 */
@RestController
@RequestMapping("/api")
public class ContentResource {

    private final Logger log = LoggerFactory.getLogger(ContentResource.class);
        
    @Inject
    private ContentService contentService;
    
    @Inject
    private ContentMapper contentMapper;
    
    /**
     * POST  /contents : Create a new content.
     *
     * @param contentDTO the contentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentDTO, or with status 400 (Bad Request) if the content has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/contents",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContentDTO> createContent(@RequestBody ContentDTO contentDTO) throws URISyntaxException {
        log.debug("REST request to save Content : {}", contentDTO);
        if (contentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("content", "idexists", "A new content cannot already have an ID")).body(null);
        }
        ContentDTO result = contentService.save(contentDTO);
        return ResponseEntity.created(new URI("/api/contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("content", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contents : Updates an existing content.
     *
     * @param contentDTO the contentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentDTO,
     * or with status 400 (Bad Request) if the contentDTO is not valid,
     * or with status 500 (Internal Server Error) if the contentDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/contents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContentDTO> updateContent(@RequestBody ContentDTO contentDTO) throws URISyntaxException {
        log.debug("REST request to update Content : {}", contentDTO);
        if (contentDTO.getId() == null) {
            return createContent(contentDTO);
        }
        ContentDTO result = contentService.save(contentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("content", contentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contents : get all the contents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contents in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/contents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ContentDTO>> getAllContents(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Contents");
        Page<Content> page = contentService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contents");
        return new ResponseEntity<>(contentMapper.contentsToContentDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /contents/:id : get the "id" content.
     *
     * @param id the id of the contentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/contents/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContentDTO> getContent(@PathVariable Long id) {
        log.debug("REST request to get Content : {}", id);
        ContentDTO contentDTO = contentService.findOne(id);
        return Optional.ofNullable(contentDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contents/:id : delete the "id" content.
     *
     * @param id the id of the contentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/contents/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        log.debug("REST request to delete Content : {}", id);
        contentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("content", id.toString())).build();
    }

}
