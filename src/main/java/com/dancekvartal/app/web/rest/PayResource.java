package com.dancekvartal.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dancekvartal.app.service.PayService;
import com.dancekvartal.app.web.rest.util.HeaderUtil;
import com.dancekvartal.app.web.rest.util.PaginationUtil;
import com.dancekvartal.app.service.dto.PayDTO;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Pay.
 */
@RestController
@RequestMapping("/api")
public class PayResource {

    private final Logger log = LoggerFactory.getLogger(PayResource.class);

    private static final String ENTITY_NAME = "pay";
        
    private final PayService payService;

    public PayResource(PayService payService) {
        this.payService = payService;
    }

    /**
     * POST  /pays : Create a new pay.
     *
     * @param payDTO the payDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payDTO, or with status 400 (Bad Request) if the pay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pays")
    @Timed
    public ResponseEntity<PayDTO> createPay(@RequestBody PayDTO payDTO) throws URISyntaxException {
        log.debug("REST request to save Pay : {}", payDTO);
        if (payDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pay cannot already have an ID")).body(null);
        }
        PayDTO result = payService.save(payDTO);
        return ResponseEntity.created(new URI("/api/pays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pays : Updates an existing pay.
     *
     * @param payDTO the payDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payDTO,
     * or with status 400 (Bad Request) if the payDTO is not valid,
     * or with status 500 (Internal Server Error) if the payDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pays")
    @Timed
    public ResponseEntity<PayDTO> updatePay(@RequestBody PayDTO payDTO) throws URISyntaxException {
        log.debug("REST request to update Pay : {}", payDTO);
        if (payDTO.getId() == null) {
            return createPay(payDTO);
        }
        PayDTO result = payService.save(payDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pays : get all the pays.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pays in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pays")
    @Timed
    public ResponseEntity<List<PayDTO>> getAllPays(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Pays");
        Page<PayDTO> page = payService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pays");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pays/:id : get the "id" pay.
     *
     * @param id the id of the payDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pays/{id}")
    @Timed
    public ResponseEntity<PayDTO> getPay(@PathVariable Long id) {
        log.debug("REST request to get Pay : {}", id);
        PayDTO payDTO = payService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(payDTO));
    }

    /**
     * DELETE  /pays/:id : delete the "id" pay.
     *
     * @param id the id of the payDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pays/{id}")
    @Timed
    public ResponseEntity<Void> deletePay(@PathVariable Long id) {
        log.debug("REST request to delete Pay : {}", id);
        payService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pays?query=:query : search for the pay corresponding
     * to the query.
     *
     * @param query the query of the pay search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/pays")
    @Timed
    public ResponseEntity<List<PayDTO>> searchPays(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Pays for query {}", query);
        Page<PayDTO> page = payService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pays");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
