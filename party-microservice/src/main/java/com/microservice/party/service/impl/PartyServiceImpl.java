package com.microservice.party.service.impl;

import com.microservice.party.dao.PartyRepository;
import com.microservice.party.dao.entities.PartyEntity;
import com.microservice.party.dto.EmailChangeDTO;
import com.microservice.party.dto.EnrollPartyDTO;
import com.microservice.party.dto.PartyDTO;
import com.microservice.party.dto.PartyMapper;
import com.microservice.party.outbox.EventPublisher;
import com.microservice.party.service.PartyService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Service Implementation that fetches / acts on PartyDTO related data.
 *
 * @author Arthur
 */
@Service
@Slf4j
public class PartyServiceImpl implements PartyService {

    /**
     * Handle to the Data Access Layer.
     */
    private final PartyRepository partyRepository;

    /**
     * Handle to the Outbox Eventing framework.
     */
    private final EventPublisher eventPublisher;

    /**
     * Autowired constructor.
     *
     * @param partyRepository
     * @param eventPublisher
     */
    @Autowired
    public PartyServiceImpl(PartyRepository partyRepository, EventPublisher eventPublisher) {
        this.partyRepository = partyRepository;
        this.eventPublisher=eventPublisher;
    }

    /**
     * Gets the Party Details for the given PartyId.
     *
     * @return PartytDTO
     */
    @Override
    public PartyDTO getParty(Integer partyId) throws Exception {
        log.info("Fetching party details for partyId: {}", partyId);

        Optional<PartyEntity> partyEntity = partyRepository.findById(partyId);
        PartyDTO partyDTO = null;

        if (partyEntity.isPresent()) {
            partyDTO = PartyMapper.INSTANCE.partyEntityToDTO(partyEntity.get());
        } else {
            throw new Exception("Party not found");
        }

        return partyDTO;
    }

    /**
     * Updates the Party Email for the given partyId.
     *
     * @param party
     * @return PartyDTO
     */
    @Override
    @Transactional
    public PartyDTO enrollParty(EnrollPartyDTO party) throws Exception {
        log.info("Enroll party details for PartyId: {}", party.getName());

        PartyEntity partyEntity = PartyMapper.INSTANCE.partyDTOToEntity(party);
        partyRepository.save(partyEntity);

        //Publish the event
        eventPublisher.fire(EventUtils.createEnrollEvent(partyEntity));

        return PartyMapper.INSTANCE.partyEntityToDTO(partyEntity);
    }

    /**
     * Updates the Party Email for the given partyId.
     *
     * @param partyId
     * @param partyEmail
     * @return PartyDTO
     */
    @Override
    @Transactional
    public PartyDTO updatePartyEmail(Integer partyId, EmailChangeDTO partyEmail) throws Exception {
        log.info("Update Email to '{}' for PartyId: {}", partyEmail.getEmail(),  partyId);

        PartyEntity partyEntity = partyRepository.getOne(partyId);
        partyEntity.setEmail(partyEmail.getEmail());
        partyRepository.save(partyEntity);

        //Publish the event
        eventPublisher.fire(EventUtils.createUpdateEmailEvent(partyEntity));

        return PartyMapper.INSTANCE.partyEntityToDTO(partyEntity);
    }
}
