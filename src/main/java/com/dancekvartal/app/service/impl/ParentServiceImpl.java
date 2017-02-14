package com.dancekvartal.app.service.impl;

import com.dancekvartal.app.service.ParentService;
import com.dancekvartal.app.domain.Parent;
import com.dancekvartal.app.repository.ParentRepository;
import com.dancekvartal.app.repository.search.ParentSearchRepository;
import com.dancekvartal.app.service.dto.ParentDTO;
import com.dancekvartal.app.service.mapper.ParentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Parent.
 */
@Service
@Transactional
public class ParentServiceImpl implements ParentService{

    private final Logger log = LoggerFactory.getLogger(ParentServiceImpl.class);
    
    private final ParentRepository parentRepository;

    private final ParentMapper parentMapper;

    private final ParentSearchRepository parentSearchRepository;

    public ParentServiceImpl(ParentRepository parentRepository, ParentMapper parentMapper, ParentSearchRepository parentSearchRepository) {
        this.parentRepository = parentRepository;
        this.parentMapper = parentMapper;
        this.parentSearchRepository = parentSearchRepository;
    }

    /**
     * Save a parent.
     *
     * @param parentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParentDTO save(ParentDTO parentDTO) {
        log.debug("Request to save Parent : {}", parentDTO);
        Parent parent = parentMapper.parentDTOToParent(parentDTO);
        parent = parentRepository.save(parent);
        ParentDTO result = parentMapper.parentToParentDTO(parent);
        parentSearchRepository.save(parent);
        return result;
    }

    /**
     *  Get all the parents.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Parents");
        Page<Parent> result = parentRepository.findAll(pageable);
        return result.map(parent -> parentMapper.parentToParentDTO(parent));
    }

    /**
     *  Get one parent by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ParentDTO findOne(Long id) {
        log.debug("Request to get Parent : {}", id);
        Parent parent = parentRepository.findOne(id);
        ParentDTO parentDTO = parentMapper.parentToParentDTO(parent);
        return parentDTO;
    }

    /**
     *  Delete the  parent by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parent : {}", id);
        parentRepository.delete(id);
        parentSearchRepository.delete(id);
    }

    /**
     * Search for the parent corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Parents for query {}", query);
        Page<Parent> result = parentSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(parent -> parentMapper.parentToParentDTO(parent));
    }
}
