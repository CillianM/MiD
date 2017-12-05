package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.PartyDTO;
import ie.mid.identityengine.enums.EntityStatus;
import ie.mid.identityengine.model.Party;
import ie.mid.identityengine.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/party")
public class PartyController {

    @Autowired
    PartyRepository partyRepository;

    @GetMapping
    @ResponseBody
    public List<PartyDTO> getParties(@PathVariable String id) {
        List<Party> parties = partyRepository.findAll();
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
        PartyDTO partyDTO = new PartyDTO();
        partyDTO.setId(party.getId());
        partyDTO.setName(party.getName());
        partyDTO.setStatus(party.getStatus());
        return partyDTO;
    }

    @PostMapping
    @ResponseBody
    public PartyDTO createParty(@RequestBody PartyDTO partyToCreate) {
        Party party = new Party();
        party.setName(partyToCreate.getName());
        party.setStatus(EntityStatus.ACTIVE.toString());
        party = partyRepository.save(party);
        partyToCreate.setId(party.getId());
        return partyToCreate;
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public PartyDTO updateParty(@PathVariable String id, @RequestBody PartyDTO partyToUpdate) {
        Party party = partyRepository.findById(id);
        party.setName(partyToUpdate.getName());
        partyRepository.save(party);
        return partyToUpdate;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public PartyDTO deleteParty(@PathVariable String id) {
        Party party = partyRepository.findById(id);
        party.setStatus(EntityStatus.DELETED.toString());
        partyRepository.save(party);
        PartyDTO partyDTO = new PartyDTO();
        partyDTO.setId(party.getId());
        partyDTO.setName(party.getName());
        partyDTO.setStatus(party.getStatus());
        return partyDTO;
    }


}
