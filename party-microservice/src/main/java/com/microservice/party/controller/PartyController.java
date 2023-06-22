package com.microservice.party.controller;

import com.microservice.party.dto.EmailChangeDTO;
import com.microservice.party.dto.EnrollPartyDTO;
import com.microservice.party.dto.PartyDTO;
import com.microservice.party.service.PartyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * This spring controller handles all the API calls made on the end point.
 * <p>
 * - /parties/{partyId}: Gets the Party Details for the given PartyId.
 * <p/>
 *
 * @author Arthur
 */
@RestController
public class PartyController {

    /**
     * Handle to the service.
     */
    private final PartyService partyService;

    /**
     * Autowired constructor.
     *
     * @param partyService
     */
    @Autowired
    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    /**
     * Gets the Party Details for the given partyId.
     *
     * @param partyId
     * @return PartyDTO
     */
    @GetMapping(value = "/parties/{partyId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PartyDTO getParty(@PathVariable Integer partyId) throws Exception {
        return this.partyService.getParty(partyId);
    }

    /**
     * Enrolls the Party.
     *
     * @param party
     * @return PartyDTO
     */
    @PostMapping(value = "/parties", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PartyDTO enrollParty(@RequestBody EnrollPartyDTO party) throws Exception {
        return this.partyService.enrollParty(party);
    }

    /**
     * Updates the party for the given partyId.
     *
     * @param partyId
     * @param partyEmail
     * @return PartyDTO
     */
    @PutMapping(value = "/parties/{partyId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PartyDTO updatePartyEmail(@PathVariable Integer partyId, @RequestBody EmailChangeDTO partyEmail) throws Exception {
        return this.partyService.updatePartyEmail(partyId, partyEmail);
    }
}
