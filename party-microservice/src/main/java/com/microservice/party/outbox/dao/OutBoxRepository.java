package com.microservice.party.outbox.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.party.outbox.models.OutBoxEntity;

/**
 * This interface provides handles to database, to perform CRUD operations on the table `OUTBOX`.
 * The table is represented by the JPA entity {@link OutBoxEntity}.
 *
 * @author Arthur
 * @see JpaRepository
 */
@Repository
public interface OutBoxRepository extends JpaRepository<OutBoxEntity, Integer> {

}
