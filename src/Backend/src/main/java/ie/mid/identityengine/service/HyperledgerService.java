package ie.mid.identityengine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import ie.mid.identityengine.enums.EntityStatus;
import ie.mid.identityengine.model.Certificate;
import ie.mid.identityengine.model.CertificateUpdate;
import ie.mid.identityengine.model.IdentifyingParty;
import ie.mid.identityengine.model.Individual;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

@Service
public class HyperledgerService {

    @Value("${mid.hyperledger}")
    private String hyperledgerUrl;
    private HttpService httpService;
    private Logger logger = LogManager.getLogger(HyperledgerService.class);

    public HyperledgerService() {
       httpService = new HttpService();
    }

    public Certificate getCertificate(String id){
        logger.debug("Sending 'GET' request to certificate for id " + id);
        httpService.setEndpointExtention("/Certificate/" + id);
        String response = httpService.sendGet();
        if (response != null) {
            return getCertificateFromJson(response);
        } else {
            logger.error("Null returning from 'GET' request to hyperledger");
            return null;
        }
    }

    public Certificate createCertificate(String partyId,String userId){
        logger.debug("Sending 'POST' request to certificate for partyId " + partyId + " and userId " + userId);
        JsonObject party = createCertificateJson(partyId,userId);
        httpService.setEndpointExtention("/Certificate");
        String response = httpService.sendPost(party.toString());
        if (response != null) {
            return getCertificateFromJson(response);
        } else {
            logger.error("Null returning from 'POST' request to hyperledger");

            return null;
        }
    }

    public CertificateUpdate updateCertificate(String certId,String updatedStatus){
        logger.debug("Sending 'POST' request to updateStatus for certificate " + certId);

        JsonObject certificateUpdate = createCertificateUpdateJson(certId,updatedStatus);
        httpService.setEndpointExtention("/UpdateStatus");
        String response = httpService.sendPost(certificateUpdate.toString());
        if (response != null) {
            return getCertificateUpdateFromJson(response);
        } else {
            logger.error("Null returning from 'POST' request to hyperledger");

            return null;
        }
    }

    public IdentifyingParty getIdentifyingParty(String id) {
        logger.debug("Sending 'GET' request to IdentifyingParty for id " + id);
        httpService.setEndpointExtention("/IdentifyingParty/" + id);
        String response = httpService.sendGet();
        if (response != null) {
            return getIdentifyingPartyFromJson(response);
        } else {
            logger.error("Null returning from 'GET' request to hyperledger");

            return null;
        }
    }

    public IdentifyingParty createIdentifyingParty() {
        logger.debug("Sending 'POST' request to IdentifyingParty");
        JsonObject party = createPartyJson();
        httpService.setEndpointExtention("/IdentifyingParty");
        String response = httpService.sendPost(party.toString());
        if (response != null) {
            return getIdentifyingPartyFromJson(response);
        } else {
            logger.error("Null returning from 'POST' request to hyperledger");

            return null;
        }
    }


    public Individual getIndividual(String id){
        logger.debug("Sending 'GET' request to Individual for id " + id);
        httpService.setEndpointExtention("/Individual/" + id);
        String response = httpService.sendGet();
        if (response != null) {
            return getIndividualFromJson(response);
        } else {
            logger.error("Null returning from 'GET' request to hyperledger");

            return null;
        }
    }

    public Individual createIndividual(){
        logger.debug("Sending 'POST' request to Individual");
        JsonObject user = createUserJson();
        httpService.setEndpointExtention("/Individual");
        String response = httpService.sendPost(user.toString());
        if (response != null) {
            return getIndividualFromJson(response);
        } else {
            logger.error("Null returning from 'POST' request to hyperledger");

            return null;
        }
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
            certificate.setOwner(owner.substring(owner.lastIndexOf('#') + 1));
            String trustee = certificate.getTrustee();
            certificate.setTrustee(trustee.substring(trustee.lastIndexOf('#') + 1));
            return certificate;
        } catch (IOException e) {
            logger.error("Error marshalling JSON " + json + " to Certificate");
            return null;
        }
    }

    private Individual getIndividualFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        Individual individual;
        try {
            individual =  mapper.readValue(json, Individual.class);
            String id = individual.getIndividualId();
            individual.setIndividualId(id.substring(id.lastIndexOf('#') + 1));
            return individual;
        } catch (IOException e) {
            logger.error("Error marshalling JSON " + json + " to Individual");
            return null;
        }
    }

    private IdentifyingParty getIdentifyingPartyFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        IdentifyingParty identifyingParty;
        try {
            identifyingParty =  mapper.readValue(json, IdentifyingParty.class);
            String id = identifyingParty.getPartyId();
            identifyingParty.setPartyId(id.substring(id.lastIndexOf('#') + 1));
            return identifyingParty;
        } catch (IOException e) {
            logger.error("Error marshalling JSON " + json + " to IdentifyingPArty");
            return null;
        }
    }

    private CertificateUpdate getCertificateUpdateFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        CertificateUpdate certificateUpdate;
        try {
            certificateUpdate =  mapper.readValue(json, CertificateUpdate.class);
            return certificateUpdate;
        } catch (IOException e) {
            logger.error("Error marshalling JSON " + json + " to CertificateUpdate");
            return null;
        }
    }
}
