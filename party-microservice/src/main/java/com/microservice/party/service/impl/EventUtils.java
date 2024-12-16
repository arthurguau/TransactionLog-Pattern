package com.microservice.party.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.party.dao.entities.PartyEntity;
import com.microservice.party.outbox.models.OutboxEvent;

/**
 * Utility class to help the service in building event payloads.
 *
 * @author Songqing Gu
 */
public class EventUtils {
	
	private static final String PARTY_ENROLLED = "PARTY_ENROLLED";
	private static final String PARTY_UPDATED = "PARTY_EMAIL_CHANGED";
	private static final String PARTY_DELETED = "PARTY_EMAIL_DELETED";
	
    /**
     * Create the event object to be published when new student is enrolled.
     *
     * @param partyEntity
     * @return OutboxEvent
     */
    public static OutboxEvent createEnrollEvent(PartyEntity partyEntity) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.convertValue(partyEntity, JsonNode.class);

        return new OutboxEvent(
                partyEntity.getPartyId(),
                PARTY_ENROLLED,
                jsonNode
        );
    }

    /**
     * Create the event object to be published when student email is changed.
     *
     * @param partyEntity
     * @return OutboxEvent
     */
    public static OutboxEvent createUpdateEmailEvent(PartyEntity partyEntity) {
        ObjectMapper mapper = new ObjectMapper();
        
        JsonNode jsonNode = mapper.convertValue(partyEntity, JsonNode.class);
        
//        ObjectNode jsonNode = mapper.createObjectNode()
//                .put("partyId", partyEntity.getPartyId())
//                .put("email",partyEntity.getEmail());

        return new OutboxEvent(
                partyEntity.getPartyId(),
                PARTY_UPDATED,
                jsonNode
        );
    }
    
    /**
     * 
     * @param partyEntity
     * @return
     */
    public static OutboxEvent deletePartyEvent(PartyEntity partyEntity) {
        ObjectMapper mapper = new ObjectMapper();
        
        JsonNode jsonNode = mapper.convertValue(partyEntity, JsonNode.class);
        return new OutboxEvent(
                partyEntity.getPartyId(),
                PARTY_DELETED,
                jsonNode
        );    	    	
    }
    
}
