package com.microservice.party.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO for holding Student related data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollPartyDTO {
	
	private static final long serialVersionUID = 100001L;

    private String name;

    private String email;

    private String address;
}
