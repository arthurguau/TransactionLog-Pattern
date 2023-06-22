package com.microservice.party.dto;

import lombok.Data;

/**
 * POJO for holding Party related data
 */
@Data
public class PartyDTO {

    private Integer partyId;

    private String name;

    private String email;

    private String address;
}
