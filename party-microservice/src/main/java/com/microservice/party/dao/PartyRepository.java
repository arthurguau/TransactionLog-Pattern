package com.microservice.party.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.party.dao.entities.PartyEntity;

/**
 * This interface provides handles to database, to perform CRUD operations on the table `STUDENT`.
 * The table is represented by the JPA entity {@link PartyEntity}.
 *
 * @author Arthur
 * @see JpaRepository
 */
@Repository
public interface PartyRepository extends JpaRepository<PartyEntity, Integer> {

}
