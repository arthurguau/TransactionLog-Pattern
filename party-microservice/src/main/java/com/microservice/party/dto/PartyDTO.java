package com.microservice.party.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO for holding Party related data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyDTO {
	
	private static final long serialVersionUID = 100001L;

    private Integer partyId;

    private String name;

    private String email;

    private String address;
}
