package com.dancekvartal.app.service.mapper;

import com.dancekvartal.app.domain.*;
import com.dancekvartal.app.service.dto.StudentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Student and its DTO StudentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StudentMapper {

    StudentDTO studentToStudentDTO(Student student);

    List<StudentDTO> studentsToStudentDTOs(List<Student> students);

    Student studentDTOToStudent(StudentDTO studentDTO);

    List<Student> studentDTOsToStudents(List<StudentDTO> studentDTOs);
}
