package com.dancekvartal.app.service.impl;

import com.dancekvartal.app.service.StudentService;
import com.dancekvartal.app.domain.Student;
import com.dancekvartal.app.repository.StudentRepository;
import com.dancekvartal.app.repository.search.StudentSearchRepository;
import com.dancekvartal.app.service.dto.StudentDTO;
import com.dancekvartal.app.service.mapper.StudentMapper;
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
 * Service Implementation for managing Student.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService{

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);
    
    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final StudentSearchRepository studentSearchRepository;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, StudentSearchRepository studentSearchRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.studentSearchRepository = studentSearchRepository;
    }

    /**
     * Save a student.
     *
     * @param studentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        log.debug("Request to save Student : {}", studentDTO);
        Student student = studentMapper.studentDTOToStudent(studentDTO);
        student = studentRepository.save(student);
        StudentDTO result = studentMapper.studentToStudentDTO(student);
        studentSearchRepository.save(student);
        return result;
    }

    /**
     *  Get all the students.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Students");
        Page<Student> result = studentRepository.findAll(pageable);
        return result.map(student -> studentMapper.studentToStudentDTO(student));
    }

    /**
     *  Get one student by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentDTO findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        Student student = studentRepository.findOne(id);
        StudentDTO studentDTO = studentMapper.studentToStudentDTO(student);
        return studentDTO;
    }

    /**
     *  Delete the  student by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.delete(id);
        studentSearchRepository.delete(id);
    }

    /**
     * Search for the student corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Students for query {}", query);
        Page<Student> result = studentSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(student -> studentMapper.studentToStudentDTO(student));
    }
}
