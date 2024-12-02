package com.microservice.party.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.microservice.party.dao.entities.PartyEntity;

/**
 * This class maps the Entity object fetched from the DataBase and builds a Data Transfer Object (DTO), which will be
 * the response from the REST API.
 *
 * @author Arthur
 * @see Mapper
 */
//@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = "spring")
//@Mapper(componentModel = "spring")
public interface PartyMapper {

    PartyMapper INSTANCE = Mappers.getMapper(PartyMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "address", target = "address")
    @Mapping(target = "partyId", ignore = true)
    PartyDTO partyEntityToDTO(PartyEntity partyEntity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "address", target = "address")   
    PartyEntity partyDTOToEntity(EnrollPartyDTO dto);
}
