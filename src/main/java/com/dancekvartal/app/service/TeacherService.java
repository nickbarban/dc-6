package com.dancekvartal.app.service;

import com.dancekvartal.app.service.dto.TeacherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Teacher.
 */
public interface TeacherService {

    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save
     * @return the persisted entity
     */
    TeacherDTO save(TeacherDTO teacherDTO);

    /**
     *  Get all the teachers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TeacherDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" teacher.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TeacherDTO findOne(Long id);

    /**
     *  Delete the "id" teacher.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the teacher corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TeacherDTO> search(String query, Pageable pageable);
}
