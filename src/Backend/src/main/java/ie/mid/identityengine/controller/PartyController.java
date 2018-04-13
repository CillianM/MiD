package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.KeyDTO;
import ie.mid.identityengine.dto.NewKeyDTO;
import ie.mid.identityengine.dto.NewPartyDTO;
import ie.mid.identityengine.dto.PartyDTO;
import ie.mid.identityengine.enums.EntityStatus;
import ie.mid.identityengine.exception.BadRequestException;
import ie.mid.identityengine.exception.HyperledgerErrorException;
import ie.mid.identityengine.exception.ResourceForbiddenException;
import ie.mid.identityengine.exception.ResourceNotFoundException;
import ie.mid.identityengine.model.IdentifyingParty;
import ie.mid.identityengine.model.Party;
import ie.mid.identityengine.repository.PartyRepository;
import ie.mid.identityengine.security.DataEncryption;
import ie.mid.identityengine.service.HyperledgerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/party")
public class PartyController {

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    KeyController keyController;

    @Autowired
    HyperledgerService hyperledgerService;

    private Logger logger = LoggerFactory.getLogger(PartyController.class);

    @GetMapping
    @ResponseBody
    public List<PartyDTO> getParties() {
        logger.debug("'GET' request to getParties()");
        List<Party> parties = partyRepository.findAll();
        if (parties == null) {
            return Collections.emptyList();
        }
        List<PartyDTO> partyDTOList = new ArrayList<>();
        parties.forEach(party -> {
            PartyDTO partyDTO = new PartyDTO();
            partyDTO.setId(party.getId());
            partyDTO.setName(party.getName());
            partyDTO.setStatus(party.getStatus());
            partyDTOList.add(partyDTO);
        });
        logger.debug("Got party list, returning...");
        return partyDTOList;
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public PartyDTO getParty(@PathVariable String id) {
        logger.debug("'GET' request to getParty() for id " + id);
        Party party = partyRepository.findById(id);
        if (party == null) {
            logger.error("No party found for id " + id);
            throw new ResourceNotFoundException();
        }
        PartyDTO partyDTO = new PartyDTO();
        partyDTO.setId(party.getId());
        partyDTO.setName(party.getName());
        partyDTO.setStatus(party.getStatus());
        logger.debug("Got party, returning...");
        return partyDTO;
    }

    @PostMapping
    @ResponseBody
    public NewPartyDTO createParty(@RequestBody PartyDTO partyToCreate) {
        logger.debug("'POST' request to createParty()");
        if (isInvalidParty(partyToCreate)) {
            logger.error("Invalid party: " + partyToCreate);
            throw new BadRequestException();
        }
        //Create Party in blockchain
        logger.debug("Creating IdentifyingParty on blockchain");
        IdentifyingParty identifyingParty = hyperledgerService.createIdentifyingParty();
        if (identifyingParty == null) {
            logger.error("Error creating party in hyperledger");
            throw new HyperledgerErrorException("Error creating party");
        }
        logger.info("Created party on hyperledger with id " + identifyingParty.getPartyId());

        logger.debug("Creating Party on server");

        Party party = new Party();
        party.setName(partyToCreate.getName());
        party.setStatus(EntityStatus.ACTIVE.toString());
        party.setNetworkId(identifyingParty.getPartyId());
        party = partyRepository.save(party);
        logger.info("Created user on server with id " + party.getId());

        partyToCreate.setId(party.getId());
        partyToCreate.setStatus(party.getStatus());

        //Create key for party
        KeyDTO keyDTO = new KeyDTO();
        keyDTO.setPublicKey(partyToCreate.getPublicKey());
        keyDTO.setUserId(partyToCreate.getId());
        NewKeyDTO createdKey = keyController.createKey(keyDTO);
        partyToCreate.setKeyId(keyDTO.getId());

        NewPartyDTO createdParty = new NewPartyDTO();
        createdParty.setId(party.getId());
        createdParty.setPublicKey(createdKey.getPublicKey());
        createdParty.setKeyId(createdKey.getId());
        createdParty.setStatus(party.getStatus());
        createdParty.setPartyToken(createdKey.getToken());
        createdParty.setName(party.getName());
        logger.debug("Party created, returning...");
        return createdParty;
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public PartyDTO updateParty(@PathVariable String id, @RequestBody String newPartyName, Authentication authentication) {
        logger.debug("'PUT' request to updateParty() for id " + id);
        Party party = partyRepository.findById(id);
        if (party == null) {
            logger.error("Party not found for id " + id);
            throw new ResourceNotFoundException();
        }
        if (!party.getId().equals(authentication.getName())) {
            logger.error("Authentication failure: " + authentication.getName() + " != " + party.getId());
            throw new ResourceForbiddenException(authentication.getName() + " is forbidden from resource " + id);
        }
        if (newPartyName == null) {
            logger.error("Null party name");
            throw new BadRequestException("Null party name");
        }
        party.setName(newPartyName);
        party = partyRepository.save(party);
        logger.debug("Party udpated, returning...");
        PartyDTO partyDTO = new PartyDTO();
        partyDTO.setName(party.getName());
        partyDTO.setStatus(party.getStatus());
        partyDTO.setId(party.getId());
        return partyDTO;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public PartyDTO deleteParty(@PathVariable String id, Authentication authentication) {
        logger.debug("'DELETE' request to deleteParty() for id " + id);
        Party party = partyRepository.findById(id);
        if (party == null) {
            logger.error("Party not found for id " + id);
            throw new ResourceNotFoundException();
        }
        if (!party.getId().equals(authentication.getName())) {
            logger.error("Authentication failure: " + authentication.getName() + " != " + party.getId());
            throw new ResourceForbiddenException(authentication.getName() + " is forbidden from resource " + id);
        }
        party.setStatus(EntityStatus.DELETED.toString());
        partyRepository.save(party);
        PartyDTO partyDTO = new PartyDTO();
        partyDTO.setId(party.getId());
        partyDTO.setName(party.getName());
        partyDTO.setStatus(party.getStatus());
        logger.debug("Party deleted, returning...");
        return partyDTO;
    }

    private boolean isInvalidParty(PartyDTO partyDTO) {
        if (partyDTO.getName() == null || partyDTO.getPublicKey() == null || DataEncryption.isInvalidPublicKey(partyDTO.getPublicKey())) {
            logger.error("PartyDTO contains null parameters: " + partyDTO.toString());
            return true;
        }
        if (partyDTO.getName().isEmpty() || partyDTO.getPublicKey().isEmpty()) {
            logger.error("PartyDTO contains empty parameters: " + partyDTO.toString());
            return true;
        }
        return false;
    }

}
