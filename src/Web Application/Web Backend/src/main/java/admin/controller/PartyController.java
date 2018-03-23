package admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import admin.dto.NewPartyDTO;
import admin.dto.PartyDTO;
import admin.enums.EntityStatus;
import admin.exception.BadRequestException;
import admin.exception.ResourceNotFoundException;
import admin.model.HttpCall;
import admin.model.Party;
import admin.repository.PartyRepository;
import admin.security.DataEncryption;
import admin.service.HttpService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

@Controller
@RequestMapping("/party")
public class PartyController {

    @Autowired
    HttpService httpService;

    @Autowired
    PartyRepository partyRepository;

    @GetMapping
    @ResponseBody
    public String getParties() {
        httpService.setEndpointExtention("/party");
        return httpService.sendGet(new HttpCall());
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public String getParty(@PathVariable String id) {
        httpService.setEndpointExtention("/party/" + id);
        return httpService.sendGet(new HttpCall());
    }

    @PostMapping
    @ResponseBody
    public NewPartyDTO createParty(@RequestBody PartyDTO partyToCreate) {
        Party party = new Party();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            party.setPrivateKey(byteToBase64(keyPair.getPrivate().getEncoded()).replace("\n",""));
            party.setPublicKey(byteToBase64(keyPair.getPublic().getEncoded()).replace("\n",""));

            partyToCreate.setPublicKey(party.getPublicKey());
            ObjectMapper mapper = new ObjectMapper();
            httpService.setEndpointExtention("/party");
            HttpCall httpCall = new HttpCall();
            httpCall.setJsonBody(mapper.writeValueAsString(partyToCreate));
            String reponse = httpService.sendPost(httpCall);
            if (reponse != null) {

                NewPartyDTO partyDTO = mapper.readValue(reponse, NewPartyDTO.class);
                party.setName(partyDTO.getName());
                party.setNetworkId(partyDTO.getId());
                party.setStatus(partyDTO.getStatus());
                party.setToken(partyDTO.getPartyToken());
                partyRepository.save(party);
                return partyDTO;

            }
            throw new BadRequestException();
        }catch(Exception e){
            throw new BadRequestException();
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public PartyDTO updateParty(@PathVariable String id, @RequestBody PartyDTO partyToUpdate) {
        Party party = partyRepository.findByNetworkId(id);
        if (party == null) throw new ResourceNotFoundException();
        if (isInvalidParty(partyToUpdate)) throw new BadRequestException();
        try{
            ObjectMapper mapper = new ObjectMapper();
            httpService.setEndpointExtention("/party/" + id);
            HttpCall httpCall = new HttpCall();
            httpCall.setAuthHeader(id, DataEncryption.encryptText(party.getToken(),party.getPrivateKey()));
            httpCall.setJsonBody(mapper.writeValueAsString(partyToUpdate));
            String reponse = httpService.sendPut(httpCall);
            if(reponse == null) throw new BadRequestException();
        }catch (Exception e){
            throw new BadRequestException();
        }
        party.setName(partyToUpdate.getName());
        partyRepository.save(party);
        return partyToUpdate;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public PartyDTO deleteParty(@PathVariable String id) {
        Party party = partyRepository.findByNetworkId(id);
        if (party == null) throw new ResourceNotFoundException();
        try{
            httpService.setEndpointExtention("/" + id);
            HttpCall httpCall = new HttpCall();
            httpCall.setAuthHeader(id, DataEncryption.encryptText(party.getToken(),party.getPrivateKey()));
            String reponse = httpService.sendDelete(httpCall);
            if(reponse == null) throw new BadRequestException();
        }catch (Exception e){
            throw new BadRequestException();
        }
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

    private String byteToBase64(byte[] array){
        return Base64.encodeBase64String(array);
    }
}
