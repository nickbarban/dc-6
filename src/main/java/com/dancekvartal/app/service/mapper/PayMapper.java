package com.dancekvartal.app.service.mapper;

import com.dancekvartal.app.domain.*;
import com.dancekvartal.app.service.dto.PayDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Pay and its DTO PayDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PayMapper {

    PayDTO payToPayDTO(Pay pay);

    List<PayDTO> paysToPayDTOs(List<Pay> pays);

    Pay payDTOToPay(PayDTO payDTO);

    List<Pay> payDTOsToPays(List<PayDTO> payDTOs);
}
