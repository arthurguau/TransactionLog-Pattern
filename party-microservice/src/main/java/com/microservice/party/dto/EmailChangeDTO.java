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
public class EmailChangeDTO {
	
	private String email;
    
}
