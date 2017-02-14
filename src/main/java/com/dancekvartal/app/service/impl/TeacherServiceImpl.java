package com.dancekvartal.app.service.impl;

import com.dancekvartal.app.service.TeacherService;
import com.dancekvartal.app.domain.Teacher;
import com.dancekvartal.app.repository.TeacherRepository;
import com.dancekvartal.app.repository.search.TeacherSearchRepository;
import com.dancekvartal.app.service.dto.TeacherDTO;
import com.dancekvartal.app.service.mapper.TeacherMapper;
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
 * Service Implementation for managing Teacher.
 */
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{

    private final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);
    
    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    private final TeacherSearchRepository teacherSearchRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper, TeacherSearchRepository teacherSearchRepository) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.teacherSearchRepository = teacherSearchRepository;
    }

    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TeacherDTO save(TeacherDTO teacherDTO) {
        log.debug("Request to save Teacher : {}", teacherDTO);
        Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
        teacher = teacherRepository.save(teacher);
        TeacherDTO result = teacherMapper.teacherToTeacherDTO(teacher);
        teacherSearchRepository.save(teacher);
        return result;
    }

    /**
     *  Get all the teachers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeacherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Teachers");
        Page<Teacher> result = teacherRepository.findAll(pageable);
        return result.map(teacher -> teacherMapper.teacherToTeacherDTO(teacher));
    }

    /**
     *  Get one teacher by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TeacherDTO findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        Teacher teacher = teacherRepository.findOne(id);
        TeacherDTO teacherDTO = teacherMapper.teacherToTeacherDTO(teacher);
        return teacherDTO;
    }

    /**
     *  Delete the  teacher by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        teacherRepository.delete(id);
        teacherSearchRepository.delete(id);
    }

    /**
     * Search for the teacher corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeacherDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Teachers for query {}", query);
        Page<Teacher> result = teacherSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(teacher -> teacherMapper.teacherToTeacherDTO(teacher));
    }
}
