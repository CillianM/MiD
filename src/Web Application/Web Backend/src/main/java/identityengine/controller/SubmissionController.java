package identityengine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import identityengine.dto.SubmissionDTO;
import identityengine.exception.BadRequestException;
import identityengine.exception.ResourceNotFoundException;
import identityengine.model.HttpCall;
import identityengine.model.Party;
import identityengine.repository.PartyRepository;
import identityengine.security.DataEncryption;
import identityengine.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/submission")
public class SubmissionController {

    @Autowired
    HttpService httpService;

    @Autowired
    PartyRepository partyRepository;


    @GetMapping(value = "/party/{partyId}")
    @ResponseBody
    public String getPartySubmissions(@PathVariable String partyId) {
        Party party = partyRepository.findByNetworkId(partyId);
        if (party == null) throw new ResourceNotFoundException();
        try{
            httpService.setEndpointExtention("/submission/party/" + partyId);
            HttpCall httpCall = new HttpCall();
            String pass = DataEncryption.encryptText(party.getToken(),party.getPrivateKey());
            String unpass = DataEncryption.decryptText(pass,party.getPublicKey());
            httpCall.setAuthHeader(partyId,pass );
            String reponse = httpService.sendGet(httpCall);
            if(reponse == null) throw new BadRequestException();
            return reponse;
        }catch (Exception e){
            throw new BadRequestException();
        }
    }


    @GetMapping(value = "/{id}")
    @ResponseBody
    public String getSubmission(@PathVariable String id, @RequestParam String partyId) {
        Party party = partyRepository.findByNetworkId(partyId);
        if (party == null) throw new ResourceNotFoundException();
        try{
            httpService.setEndpointExtention("/submission/" + id);
            HttpCall httpCall = new HttpCall();
            httpCall.setAuthHeader(partyId, DataEncryption.encryptText(party.getToken(),party.getPrivateKey()));
            String reponse = httpService.sendGet(httpCall);
            if(reponse == null) throw new BadRequestException();
            return reponse;
        }catch (Exception e){
            throw new BadRequestException();
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public String updateSubmission(@PathVariable String id, @RequestBody SubmissionDTO submissionToUpdate) {
        String partyId = submissionToUpdate.getPartyId();
        Party party = partyRepository.findByNetworkId(partyId);
        if (party == null) throw new ResourceNotFoundException();
        try{
            ObjectMapper mapper = new ObjectMapper();
            httpService.setEndpointExtention("/submission/" + id);
            HttpCall httpCall = new HttpCall();
            httpCall.setAuthHeader(partyId, DataEncryption.encryptText(party.getToken(),party.getPrivateKey()));
            httpCall.setJsonBody(mapper.writeValueAsString(submissionToUpdate));
            String reponse = httpService.sendPut(httpCall);
            if(reponse == null) throw new BadRequestException();
            return reponse;
        }catch (Exception e){
            throw new BadRequestException();
        }
    }
}
