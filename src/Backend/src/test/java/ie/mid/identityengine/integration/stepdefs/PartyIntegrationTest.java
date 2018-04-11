package ie.mid.identityengine.integration.stepdefs;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import ie.mid.identityengine.dto.NewPartyDTO;
import ie.mid.identityengine.dto.PartyDTO;
import ie.mid.identityengine.integration.model.HttpCall;
import ie.mid.identityengine.integration.model.StoredParty;
import ie.mid.identityengine.integration.util.HttpUtil;
import ie.mid.identityengine.integration.util.StorageUtil;
import ie.mid.identityengine.security.DataEncryption;
import ie.mid.identityengine.security.KeyUtil;

import java.security.KeyPair;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PartyIntegrationTest {

    private static final String NEW_NAME = "NEW_TEST_PARTY";
    private static final String PUBLIC_KEY = "PUBLIC_KEY";
    private static final String NAME = "TEST_PARTY";

    @When("^the client calls /party with party data$")
    public void theClientCallsPartyWithPartyData() throws Throwable {
        HttpUtil.endpointExtention = "/party";
        StoredParty party = new StoredParty();
        KeyPair keyPair = KeyUtil.generateKeyPair();
        if (keyPair == null) throw new NullPointerException();
        party.setPrivateKey(KeyUtil.byteToBase64(keyPair.getPrivate().getEncoded()).replace("\n", ""));
        party.setPublicKey(KeyUtil.byteToBase64(keyPair.getPublic().getEncoded()).replace("\n", ""));
        party.setName(NAME);
        ObjectMapper mapper = new ObjectMapper();
        PartyDTO partyToCreate = new PartyDTO();
        partyToCreate.setName(party.getName());
        partyToCreate.setPublicKey(party.getPublicKey());
        StorageUtil.storedParty = party;
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(partyToCreate)));
    }

    @And("^the client receives back a reference to the created party$")
    public void theClientReceivesBackAReferenceToTheCreatedParty() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(NAME));
        ObjectMapper mapper = new ObjectMapper();
        NewPartyDTO newPartyDTO = mapper.readValue(HttpUtil.lastResponse, NewPartyDTO.class);
        StorageUtil.storedParty.setToken(newPartyDTO.getPartyToken());
        StorageUtil.storedParty.setId(newPartyDTO.getId());
    }

    @When("^the client calls /party with no party data$")
    public void theClientCallsPartyWithNoPartyData() throws Throwable {
        HttpUtil.endpointExtention = "/user";
        //Create user to be sent
        ObjectMapper mapper = new ObjectMapper();
        PartyDTO partyToCreate = new PartyDTO();
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(partyToCreate)));
    }

    @When("^the client calls /party with incorrect party key$")
    public void theClientCallsPartyWithIncorrectPartyKey() throws Throwable {
        HttpUtil.endpointExtention = "/user";
        ObjectMapper mapper = new ObjectMapper();
        PartyDTO partyToCreate = new PartyDTO();
        partyToCreate.setPublicKey(PUBLIC_KEY);
        partyToCreate.setName(NAME);
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(partyToCreate)));
    }

    @When("^the client calls /party with existing partyId$")
    public void theClientCallsUserWithExistingPartyId() throws Throwable {
        HttpUtil.endpointExtention = "/party" + "/" + StorageUtil.storedParty.getId();
        HttpUtil.sendGet(new HttpCall());
    }

    @And("^the client receives back data on the referenced party$")
    public void theClientReceivesBackDataOnTheReferencedParty() throws Throwable {
        assertTrue(HttpUtil.lastResponse.contains(StorageUtil.storedParty.getName()));
    }

    @When("^the client calls /party with nonexistent partyId$")
    public void theClientCallsPartyWithNonexistentPartyId() throws Throwable {
        HttpUtil.endpointExtention = "/party" + "/" + StorageUtil.storedParty.getName();
        HttpUtil.sendGet(new HttpCall());
    }

    @When("^the client calls /party with an updated name$")
    public void theClientCallsUserTokenWithAnUpdatedName() throws Throwable {
        HttpUtil.endpointExtention = "/party" + "/" + StorageUtil.storedParty.getId();
        String password = DataEncryption.encryptText(StorageUtil.storedParty.getToken(), StorageUtil.storedParty.getPrivateKey());
        ObjectMapper mapper = new ObjectMapper();
        HttpUtil.sendPut(new HttpCall(mapper.writeValueAsString(NEW_NAME), StorageUtil.storedParty.getId(), password));
    }

    @And("^the client receives back data on the updated party$")
    public void theClientReceivesBackDataOnTheUpdatedParty() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(NEW_NAME));
        ObjectMapper mapper = new ObjectMapper();
        NewPartyDTO newPartyDTO = mapper.readValue(HttpUtil.lastResponse, NewPartyDTO.class);
        StorageUtil.storedParty.setName(newPartyDTO.getName());
    }

    @When("^the client calls /party without correct credentials$")
    public void theClientCallsPartyWithoutCorrectCredentials() throws Throwable {
        HttpUtil.endpointExtention = "/party" + "/" + StorageUtil.storedParty.getId();
        String password = StorageUtil.storedParty.getId();
        ObjectMapper mapper = new ObjectMapper();
        HttpUtil.sendPut(new HttpCall(mapper.writeValueAsString(NEW_NAME), StorageUtil.storedParty.getId(), password));
    }
}
