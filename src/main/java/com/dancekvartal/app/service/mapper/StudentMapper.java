package com.dancekvartal.app.service.mapper;

import com.dancekvartal.app.domain.*;
import com.dancekvartal.app.service.dto.StudentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Student and its DTO StudentDTO.
 */
@Mapper(componentModel = "spring", uses = {SubjectMapper.class, ParentMapper.class, })
public interface StudentMapper {

    StudentDTO studentToStudentDTO(Student student);

    List<StudentDTO> studentsToStudentDTOs(List<Student> students);

    @Mapping(target = "pays", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    Student studentDTOToStudent(StudentDTO studentDTO);

    List<Student> studentDTOsToStudents(List<StudentDTO> studentDTOs);

    default Subject subjectFromId(Long id) {
        if (id == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setId(id);
        return subject;
    }

    default Parent parentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Parent parent = new Parent();
        parent.setId(id);
        return parent;
    }
}
