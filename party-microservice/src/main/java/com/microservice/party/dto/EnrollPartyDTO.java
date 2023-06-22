package com.microservice.party.dto;

import lombok.Data;

/**
 * POJO for holding Student related data
 */
@Data
public class EnrollPartyDTO {

    private String name;

    private String email;

    private String address;
}
