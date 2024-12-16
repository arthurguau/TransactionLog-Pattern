package com.microservice.party.dao.entities;

import java.io.Serializable;

//import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Entity that maps the PARTY table.
 */
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "PARTY")
public class PartyEntity implements Serializable{
	
	private static final long serialVersionUID = 100001L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    //@GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "ID")
    private Integer partyId;

	@Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ADDRESS")
    private String address;
}
