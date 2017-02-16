package com.dancekvartal.app.service.mapper;

import com.dancekvartal.app.domain.*;
import com.dancekvartal.app.service.dto.TeacherDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Teacher and its DTO TeacherDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeacherMapper {

    TeacherDTO teacherToTeacherDTO(Teacher teacher);

    List<TeacherDTO> teachersToTeacherDTOs(List<Teacher> teachers);

    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    @Mapping(target = "pays", ignore = true)
    Teacher teacherDTOToTeacher(TeacherDTO teacherDTO);

    List<Teacher> teacherDTOsToTeachers(List<TeacherDTO> teacherDTOs);
}
