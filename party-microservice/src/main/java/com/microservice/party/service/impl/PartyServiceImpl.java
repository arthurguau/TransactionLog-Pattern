package com.microservice.party.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.party.dao.PartyRepository;
import com.microservice.party.dao.entities.PartyEntity;
import com.microservice.party.dto.EmailChangeDTO;
import com.microservice.party.dto.EnrollPartyDTO;
import com.microservice.party.dto.PartyDTO;
import com.microservice.party.dto.PartyMapper;
import com.microservice.party.outbox.EventPublisher;
import com.microservice.party.service.PartyService;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation that fetches / acts on PartyDTO related data.
 *
 * @author Arthur
 */
@Service
@Slf4j
public class PartyServiceImpl implements PartyService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

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
        logger.info("Fetching party details for partyId: {}", partyId);

        Optional<PartyEntity> partyEntity = partyRepository.findById(partyId);
        PartyDTO partyDTO = null;

        if (partyEntity.isPresent()) {
        	
        	partyDTO =  new PartyDTO();
        	partyDTO.setName(partyEntity.get().getName());
        	partyDTO.setEmail(partyEntity.get().getEmail());
        	partyDTO.setPartyId(partyEntity.get().getPartyId());
        	partyDTO.setAddress(partyEntity.get().getAddress());
            //partyDTO = PartyMapper.INSTANCE.partyEntityToDTO(partyEntity.get());
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
        logger.info("Enroll party details for PartyId: {}", party.getName());
        logger.info(party.toString());
        
        //PartyEntity partyEntity = PartyMapper.INSTANCE.partyDTOToEntity(party);
        PartyEntity partyEntity =  new PartyEntity();
        partyEntity.setAddress(party.getAddress());
        partyEntity.setEmail(party.getEmail());
        partyEntity.setName(party.getName());
        
        logger.info(partyEntity.toString());
        
        partyRepository.save(partyEntity);

        //Publish the event
        eventPublisher.fire(EventUtils.createEnrollEvent(partyEntity));
        
        PartyDTO partyDTO =  new PartyDTO();
    	partyDTO.setName(partyEntity.getName());
    	partyDTO.setEmail(partyEntity.getEmail());
    	partyDTO.setPartyId(partyEntity.getPartyId());
    	partyDTO.setAddress(partyEntity.getAddress());        

        //return PartyMapper.INSTANCE.partyEntityToDTO(partyEntity);
        return partyDTO;
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
        logger.info("Update Email to '{}' for PartyId: {}", partyEmail.getEmail(),  partyId);

        PartyEntity partyEntity = partyRepository.getOne(partyId);
        partyEntity.setEmail(partyEmail.getEmail());
        partyEntity = partyRepository.save(partyEntity);

        //Publish the event
        eventPublisher.fire(EventUtils.createUpdateEmailEvent(partyEntity));
        
        PartyDTO partyDTO =  new PartyDTO();
    	partyDTO.setName(partyEntity.getName());
    	partyDTO.setEmail(partyEntity.getEmail());
    	partyDTO.setPartyId(partyEntity.getPartyId());
    	partyDTO.setAddress(partyEntity.getAddress());

        //return PartyMapper.INSTANCE.partyEntityToDTO(partyEntity);
        return partyDTO;
        
    }
}
