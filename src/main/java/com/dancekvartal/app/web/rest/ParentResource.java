package com.dancekvartal.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dancekvartal.app.service.ParentService;
import com.dancekvartal.app.web.rest.util.HeaderUtil;
import com.dancekvartal.app.web.rest.util.PaginationUtil;
import com.dancekvartal.app.service.dto.ParentDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Parent.
 */
@RestController
@RequestMapping("/api")
public class ParentResource {

    private final Logger log = LoggerFactory.getLogger(ParentResource.class);

    private static final String ENTITY_NAME = "parent";
        
    private final ParentService parentService;

    public ParentResource(ParentService parentService) {
        this.parentService = parentService;
    }

    /**
     * POST  /parents : Create a new parent.
     *
     * @param parentDTO the parentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parentDTO, or with status 400 (Bad Request) if the parent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parents")
    @Timed
    public ResponseEntity<ParentDTO> createParent(@Valid @RequestBody ParentDTO parentDTO) throws URISyntaxException {
        log.debug("REST request to save Parent : {}", parentDTO);
        if (parentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new parent cannot already have an ID")).body(null);
        }
        ParentDTO result = parentService.save(parentDTO);
        return ResponseEntity.created(new URI("/api/parents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parents : Updates an existing parent.
     *
     * @param parentDTO the parentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parentDTO,
     * or with status 400 (Bad Request) if the parentDTO is not valid,
     * or with status 500 (Internal Server Error) if the parentDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parents")
    @Timed
    public ResponseEntity<ParentDTO> updateParent(@Valid @RequestBody ParentDTO parentDTO) throws URISyntaxException {
        log.debug("REST request to update Parent : {}", parentDTO);
        if (parentDTO.getId() == null) {
            return createParent(parentDTO);
        }
        ParentDTO result = parentService.save(parentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parents : get all the parents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of parents in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/parents")
    @Timed
    public ResponseEntity<List<ParentDTO>> getAllParents(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Parents");
        Page<ParentDTO> page = parentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /parents/:id : get the "id" parent.
     *
     * @param id the id of the parentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/parents/{id}")
    @Timed
    public ResponseEntity<ParentDTO> getParent(@PathVariable Long id) {
        log.debug("REST request to get Parent : {}", id);
        ParentDTO parentDTO = parentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parentDTO));
    }

    /**
     * DELETE  /parents/:id : delete the "id" parent.
     *
     * @param id the id of the parentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parents/{id}")
    @Timed
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        log.debug("REST request to delete Parent : {}", id);
        parentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/parents?query=:query : search for the parent corresponding
     * to the query.
     *
     * @param query the query of the parent search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/parents")
    @Timed
    public ResponseEntity<List<ParentDTO>> searchParents(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Parents for query {}", query);
        Page<ParentDTO> page = parentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/parents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
