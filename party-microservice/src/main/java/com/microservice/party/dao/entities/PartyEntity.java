package com.microservice.party.dao.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity that maps the PARTY table.
 */
@Data
@Entity
@Table(name = "PARTY")
public class PartyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "ID")
    private Integer partyId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ADDRESS")
    private String address;
}
