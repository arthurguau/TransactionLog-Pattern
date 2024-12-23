package com.microservice.party.outbox;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.microservice.party.outbox.dao.OutBoxRepository;
import com.microservice.party.outbox.models.OutBoxEntity;
import com.microservice.party.outbox.models.OutboxEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * Event Service responsible for persisting the event in the database.
 *
 * @author Arthur
 */
@Service
@Slf4j
public class EventService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Handle to the Data Access Layer.
     */
    private final OutBoxRepository outBoxRepository;

    /**
     * Autowired constructor.
     *
     * @param outBoxRepository
     */
    @Autowired
    public EventService(OutBoxRepository outBoxRepository) {
        this.outBoxRepository = outBoxRepository;
    }

    /**
     * This method handles all the events fired by the 'EventPublisher'. The method listens to events
     * and persists them in the database.
     *
     * @param event
     */
    @EventListener
    public void handleOutboxEvent(OutboxEvent event) {

        UUID uuid = UUID.randomUUID();
        OutBoxEntity entity = new OutBoxEntity(
                uuid,
                event.getAggregateId(),
                event.getEventType(),
                event.getPayload(), 
                new Date()
        );

        logger.info("Handling event : {}.", entity);

        outBoxRepository.save(entity);

        /*
         * Delete the event once written, so that the outbox doesn't grow.
         * The CDC eventing polls the database log entry and not the table in the database.
         */
        //outBoxRepository.delete(entity);
    }
}
