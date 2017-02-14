package com.dancekvartal.app.service;

import com.dancekvartal.app.service.dto.PayDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Pay.
 */
public interface PayService {

    /**
     * Save a pay.
     *
     * @param payDTO the entity to save
     * @return the persisted entity
     */
    PayDTO save(PayDTO payDTO);

    /**
     *  Get all the pays.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PayDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pay.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PayDTO findOne(Long id);

    /**
     *  Delete the "id" pay.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pay corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PayDTO> search(String query, Pageable pageable);
}
