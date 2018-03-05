package ie.mid.identityengine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import ie.mid.identityengine.dto.CertificateDTO;
import ie.mid.identityengine.enums.EntityStatus;
import ie.mid.identityengine.model.Certificate;
import ie.mid.identityengine.model.CertificateUpdate;
import ie.mid.identityengine.model.IdentifyingParty;
import ie.mid.identityengine.model.Individual;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

@Service
public class HyperledgerService {

    private HttpService httpService;

    public HyperledgerService() {
       httpService = new HttpService();
    }

    public Certificate getCertificate(String id){
        httpService.setEndpointExtention("/Certificate/" + id);
        String response = httpService.sendGet();
        if(response != null) return getCertificateFromJson(response);
        else return null;
    }

    public Certificate createCertificate(String partyId,String userId){
        JsonObject party = createCertificateJson(partyId,userId);
        httpService.setEndpointExtention("/Certificate");
        String response = httpService.sendPost(party.toString());
        if(response != null) return getCertificateFromJson(response);
        else return null;
    }

    public CertificateUpdate updateCertificate(String certId,String updatedStatus){
        JsonObject certificateUpdate = createCertificateUpdateJson(certId,updatedStatus);
        httpService.setEndpointExtention("/UpdateStatus");
        String response = httpService.sendPost(certificateUpdate.toString());
        if(response != null) return getCertificateUpdateFromJson(response);
        else return null;
    }

    public IdentifyingParty getIdentifyingParty(String id) {
        httpService.setEndpointExtention("/IdentifyingParty/" + id);
        String response = httpService.sendGet();
        if (response != null) return getIdentifyingPartyFromJson(response);
        else return null;
    }

    public IdentifyingParty createIdentifyingParty() {
        JsonObject party = createPartyJson();
        httpService.setEndpointExtention("/IdentifyingParty");
        String response = httpService.sendPost(party.toString());
        if (response != null) return getIdentifyingPartyFromJson(response);
        else return null;
    }


    public Individual getIndividual(String id){
        httpService.setEndpointExtention("/Individual/" + id);
        String response = httpService.sendGet();
        if(response != null) return getIndividualFromJson(response);
        else return null;
    }

    public Individual createIndividual(){
        JsonObject user = createUserJson();
        httpService.setEndpointExtention("/Individual");
        String response = httpService.sendPost(user.toString());
        if(response != null) return getIndividualFromJson(response);
        else return null;
    }

    private JsonObject createPartyJson(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("$class","ie.mid.IdentifyingParty");
        jsonObject.addProperty("partyId", UUID.randomUUID().toString());
        return jsonObject;
    }

    private JsonObject createUserJson(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("$class","ie.mid.Individual");
        jsonObject.addProperty("individualId", UUID.randomUUID().toString());
        return jsonObject;
    }

    private JsonObject createCertificateJson(String partyId,String userId){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("$class","ie.mid.Certificate");
        jsonObject.addProperty("certId", UUID.randomUUID().toString());
        jsonObject.addProperty("dateCreated",timestamp.toString());
        jsonObject.addProperty("status", EntityStatus.ACTIVE.toString());
        jsonObject.addProperty("trustee", "resource:ie.mid.IdentifyingParty#" + partyId);
        jsonObject.addProperty("owner", "resource:ie.mid.Individual#" + userId);
        return jsonObject;
    }

    private JsonObject createCertificateUpdateJson(String certId,String updatedStatus){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("$class","ie.mid.Certificate");
        jsonObject.addProperty("certificate", "resource:ie.mid.Certificate#" + certId);
        jsonObject.addProperty("newStatus",updatedStatus);
        return jsonObject;
    }

    private Certificate getCertificateFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        Certificate certificate;
        try {
            certificate =  mapper.readValue(json, Certificate.class);
            String owner = certificate.getOwner();
            certificate.setOwner(owner.substring(owner.lastIndexOf("#") + 1));
            String trustee = certificate.getTrustee();
            certificate.setTrustee(trustee.substring(trustee.lastIndexOf("#") + 1));
            return certificate;
        } catch (IOException e) {
        }
        return null;
    }

    private Individual getIndividualFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        Individual individual;
        try {
            individual =  mapper.readValue(json, Individual.class);
            String id = individual.getIndividualId();
            individual.setIndividualId(id.substring(id.lastIndexOf("#") + 1 ));
            return individual;
        } catch (IOException e) {
        }
        return null;
    }

    private IdentifyingParty getIdentifyingPartyFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        IdentifyingParty identifyingParty;
        try {
            identifyingParty =  mapper.readValue(json, IdentifyingParty.class);
            String id = identifyingParty.getPartyId();
            identifyingParty.setPartyId(id.substring(id.lastIndexOf("#") + 1 ));
            return identifyingParty;
        } catch (IOException e) {
        }
        return null;
    }

    private CertificateUpdate getCertificateUpdateFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        CertificateUpdate certificateUpdate;
        try {
            certificateUpdate =  mapper.readValue(json, CertificateUpdate.class);
            return certificateUpdate;
        } catch (IOException e) {
        }
        return null;
    }
}
