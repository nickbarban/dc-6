package com.dancekvartal.app.service;

import com.dancekvartal.app.service.dto.ParentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Parent.
 */
public interface ParentService {

    /**
     * Save a parent.
     *
     * @param parentDTO the entity to save
     * @return the persisted entity
     */
    ParentDTO save(ParentDTO parentDTO);

    /**
     *  Get all the parents.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ParentDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" parent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ParentDTO findOne(Long id);

    /**
     *  Delete the "id" parent.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the parent corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ParentDTO> search(String query, Pageable pageable);
}
