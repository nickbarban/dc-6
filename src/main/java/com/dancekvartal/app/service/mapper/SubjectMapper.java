package com.dancekvartal.app.service.mapper;

import com.dancekvartal.app.domain.*;
import com.dancekvartal.app.service.dto.SubjectDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Subject and its DTO SubjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubjectMapper {

    SubjectDTO subjectToSubjectDTO(Subject subject);

    List<SubjectDTO> subjectsToSubjectDTOs(List<Subject> subjects);

    Subject subjectDTOToSubject(SubjectDTO subjectDTO);

    List<Subject> subjectDTOsToSubjects(List<SubjectDTO> subjectDTOs);
}
