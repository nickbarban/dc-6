package com.dancekvartal.app.service.mapper;

import com.dancekvartal.app.domain.*;
import com.dancekvartal.app.service.dto.SubjectDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Subject and its DTO SubjectDTO.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class, })
public interface SubjectMapper {

    @Mapping(source = "teacher.id", target = "teacherId")
    SubjectDTO subjectToSubjectDTO(Subject subject);

    List<SubjectDTO> subjectsToSubjectDTOs(List<Subject> subjects);

    @Mapping(source = "teacherId", target = "teacher")
    Subject subjectDTOToSubject(SubjectDTO subjectDTO);

    List<Subject> subjectDTOsToSubjects(List<SubjectDTO> subjectDTOs);

    default Teacher teacherFromId(Long id) {
        if (id == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setId(id);
        return teacher;
    }

    default Student studentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
