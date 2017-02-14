package com.dancekvartal.app.service.mapper;

import com.dancekvartal.app.domain.*;
import com.dancekvartal.app.service.dto.ParentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Parent and its DTO ParentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParentMapper {

    ParentDTO parentToParentDTO(Parent parent);

    List<ParentDTO> parentsToParentDTOs(List<Parent> parents);

    Parent parentDTOToParent(ParentDTO parentDTO);

    List<Parent> parentDTOsToParents(List<ParentDTO> parentDTOs);
}
