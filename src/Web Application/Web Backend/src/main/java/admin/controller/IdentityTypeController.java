package admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import admin.dto.IdentityTypeDTO;
import admin.exception.BadRequestException;
import admin.exception.ResourceNotFoundException;
import admin.model.HttpCall;
import admin.model.Party;
import admin.repository.PartyRepository;
import admin.security.DataEncryption;
import admin.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/identitytype")
public class IdentityTypeController {

    @Autowired
    HttpService httpService;

    @Autowired
    PartyRepository partyRepository;

    @GetMapping()
    @ResponseBody
    public String getIdentityTypes() {
        httpService.setEndpointExtention("/identitytype");
        return httpService.sendGet(new HttpCall());
    }

    @PostMapping()
    @ResponseBody
    public String createIdentityType(@RequestBody IdentityTypeDTO identityTypeToCreate) {
        String id = identityTypeToCreate.getPartyId();
        Party party = partyRepository.findByNetworkId(id);
        if (party == null) throw new ResourceNotFoundException();
        try{
            ObjectMapper mapper = new ObjectMapper();
            httpService.setEndpointExtention("/identitytype");
            HttpCall httpCall = new HttpCall();
            httpCall.setAuthHeader(id, DataEncryption.encryptText(party.getToken(),party.getPrivateKey()));
            httpCall.setJsonBody(mapper.writeValueAsString(identityTypeToCreate));
            String reponse = httpService.sendPost(httpCall);
            if(reponse == null) throw new BadRequestException();
            return reponse;
        }catch (Exception e){
            throw new BadRequestException();
        }
    }

    @GetMapping(value = "/party/{partyId}")
    @ResponseBody
    public String getPartyIdentityTypes(@PathVariable String partyId) {
        try{
            httpService.setEndpointExtention("/identitytype/party/" + partyId);
            HttpCall httpCall = new HttpCall();
            String reponse = httpService.sendGet(httpCall);
            if(reponse == null) throw new BadRequestException();
            return reponse;
        }catch (Exception e){
            throw new BadRequestException();
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public String getIdentityType(@PathVariable String id) {
        try{
            httpService.setEndpointExtention("/identitytype/" + id);
            HttpCall httpCall = new HttpCall();
            String reponse = httpService.sendGet(httpCall);
            if(reponse == null) throw new BadRequestException();
            return reponse;
        }catch (Exception e){
            throw new BadRequestException();
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public String updateIdentityType(@PathVariable String id, @RequestBody IdentityTypeDTO identityTypeDTO) {
        String partyId = identityTypeDTO.getPartyId();
        Party party = partyRepository.findByNetworkId(partyId);
        if (party == null) throw new ResourceNotFoundException();
        try{
            ObjectMapper mapper = new ObjectMapper();
            httpService.setEndpointExtention("/identitytype/" + id);
            HttpCall httpCall = new HttpCall();
            httpCall.setAuthHeader(partyId, DataEncryption.encryptText(party.getToken(),party.getPrivateKey()));
            httpCall.setJsonBody(mapper.writeValueAsString(identityTypeDTO));
            String reponse = httpService.sendPut(httpCall);
            if(reponse == null) throw new BadRequestException();
            return reponse;
        }catch (Exception e){
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public String deleteIdentityType(@PathVariable String id, @RequestParam String partyId) {
        Party party = partyRepository.findByNetworkId(partyId);
        if (party == null) throw new ResourceNotFoundException();
        try{
            httpService.setEndpointExtention("/identitytype/" + id);
            HttpCall httpCall = new HttpCall();
            httpCall.setAuthHeader(partyId, DataEncryption.encryptText(party.getToken(),party.getPrivateKey()));
            String reponse = httpService.sendDelete(httpCall);
            if(reponse == null) throw new BadRequestException();
            return reponse;
        }catch (Exception e){
            throw new BadRequestException();
        }
    }
}
