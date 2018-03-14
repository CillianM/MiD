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
import ie.mid.identityengine.service.HyperledgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping
    @ResponseBody
    public List<PartyDTO> getParties() {
        List<Party> parties = partyRepository.findAll();
        if (parties == null) throw new ResourceNotFoundException();
        List<PartyDTO> partyDTOList = new ArrayList<>();
        parties.forEach(party -> {
            PartyDTO partyDTO = new PartyDTO();
            partyDTO.setId(party.getId());
            partyDTO.setName(party.getName());
            partyDTO.setStatus(party.getStatus());
            partyDTOList.add(partyDTO);
        });
        return partyDTOList;
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public PartyDTO getParty(@PathVariable String id) {
        Party party = partyRepository.findById(id);
        if (party == null) throw new ResourceNotFoundException();
        PartyDTO partyDTO = new PartyDTO();
        partyDTO.setId(party.getId());
        partyDTO.setName(party.getName());
        partyDTO.setStatus(party.getStatus());
        return partyDTO;
    }

    @PostMapping
    @ResponseBody
    public NewPartyDTO createParty(@RequestBody PartyDTO partyToCreate) {
        if (isInvalidParty(partyToCreate)) throw new BadRequestException();
        //Create Party in blockchain
        IdentifyingParty identifyingParty = hyperledgerService.createIdentifyingParty();
        if(identifyingParty == null) throw new HyperledgerErrorException("Error creating party");

        Party party = new Party();
        party.setName(partyToCreate.getName());
        party.setStatus(EntityStatus.ACTIVE.toString());
        party.setNetworkId(identifyingParty.getPartyId());
        party = partyRepository.save(party);
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
        return createdParty;
    }

    @PreAuthorize("#partyToUpdate.id == authentication.name")
    @PutMapping(value = "/{id}")
    @ResponseBody
    public PartyDTO updateParty(@PathVariable String id, @RequestBody PartyDTO partyToUpdate) {
        Party party = partyRepository.findById(id);
        if (party == null) throw new ResourceNotFoundException();
        if (isInvalidParty(partyToUpdate)) throw new BadRequestException();
        party.setName(partyToUpdate.getName());
        partyRepository.save(party);
        return partyToUpdate;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public PartyDTO deleteParty(@PathVariable String id, Authentication authentication) {
        Party party = partyRepository.findById(id);
        if (party == null) throw new ResourceNotFoundException();
        if (!party.getId().equals(authentication.getName()))
            throw new ResourceForbiddenException(authentication.getName() + " is forbidden from resource " + id);
        party.setStatus(EntityStatus.DELETED.toString());
        partyRepository.save(party);
        PartyDTO partyDTO = new PartyDTO();
        partyDTO.setId(party.getId());
        partyDTO.setName(party.getName());
        partyDTO.setStatus(party.getStatus());
        return partyDTO;
    }

    private boolean isInvalidParty(PartyDTO partyDTO) {
        return partyDTO.getName() == null || partyDTO.getPublicKey() == null;
    }

}
