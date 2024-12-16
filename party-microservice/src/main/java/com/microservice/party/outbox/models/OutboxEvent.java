package com.microservice.party.outbox.models;

import com.fasterxml.jackson.databind.JsonNode;
//import io.hypersistence.utils.hibernate.type.json.JsonType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO for holding the OutboxEvent to be published.
 *
 * @author Arthur
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {
	
	private static final long serialVersionUID = 100001L;
	
	private Integer aggregateId;

    private String eventType;

    private JsonNode payload;
}
