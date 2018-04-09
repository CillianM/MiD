package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import ie.mid.identityengine.dto.InformationRequestDTO;
import ie.mid.identityengine.dto.NewUserDTO;
import ie.mid.identityengine.dto.RequestDTO;
import ie.mid.identityengine.dto.UserDTO;
import ie.mid.identityengine.enums.RequestStatus;
import ie.mid.identityengine.security.DataEncryption;
import ie.mid.identityengine.security.KeyUtil;
import integration.model.HttpCall;
import integration.model.StoredRequest;
import integration.model.StoredUser;
import integration.util.HttpUtil;
import integration.util.StorageUtil;
import org.junit.runner.RunWith;

import java.security.KeyPair;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features")
public class RequestIntegrationTest {

    private static final String FCM_TOKEN = "FCM_TOKEN";
    private static final String NAME = "TEST_REQUEST_USER";

    @Then("^another client creates a user$")
    public void anotherClientCreatesAUser() throws Throwable {
        HttpUtil.endpointExtention = "/user";
        //Create user to be sent
        StoredUser user = new StoredUser();
        KeyPair keyPair = KeyUtil.generateKeyPair();
        if (keyPair == null) throw new NullPointerException();
        user.setPrivateKey(KeyUtil.byteToBase64(keyPair.getPrivate().getEncoded()).replace("\n", ""));
        user.setPublicKey(KeyUtil.byteToBase64(keyPair.getPublic().getEncoded()).replace("\n", ""));
        user.setName(NAME);
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userToCreate = new UserDTO();
        userToCreate.setFcmToken(FCM_TOKEN);
        userToCreate.setPublicKey(user.getPublicKey());
        userToCreate.setNickname(user.getName());
        StorageUtil.toRequestUser = user;
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(userToCreate)));
    }

    @And("^the client receives back a reference to the second user$")
    public void theClientReceivesBackAReferenceToTheSecondUser() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(FCM_TOKEN));
        ObjectMapper mapper = new ObjectMapper();
        NewUserDTO newUserDTO = mapper.readValue(HttpUtil.lastResponse, NewUserDTO.class);
        StorageUtil.toRequestUser.setToken(newUserDTO.getUserToken());
        StorageUtil.toRequestUser.setId(newUserDTO.getId());
    }


    @When("^the requesting client sends a request to another user$")
    public void theRequestingClientSendsARequestToAnotherUser() throws Throwable {
        HttpUtil.endpointExtention = "/request";
        //Create user to be sent
        StoredRequest storedRequest = new StoredRequest();
        storedRequest.setSenderId(StorageUtil.toRequestUser.getId());
        storedRequest.setReceiverId(StorageUtil.storedUser.getId());
        storedRequest.setIdentityTypeId(StorageUtil.storedIdentityType.getId());
        storedRequest.setFieldsRequested("FIRSTNAME");
        ObjectMapper mapper = new ObjectMapper();
        InformationRequestDTO informationRequestDTO = new InformationRequestDTO();
        informationRequestDTO.setIdentityTypeFields(storedRequest.getFieldsRequested());
        informationRequestDTO.setIndentityTypeId(storedRequest.getIdentityTypeId());
        informationRequestDTO.setSenderId(storedRequest.getSenderId());
        informationRequestDTO.setRecipientId(storedRequest.getReceiverId());
        StorageUtil.storedRequest = storedRequest;
        String password = DataEncryption.encryptText(StorageUtil.toRequestUser.getToken(), StorageUtil.toRequestUser.getPrivateKey());
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(informationRequestDTO), StorageUtil.toRequestUser.getId(), password));
    }

    @Then("^the requesting client receives back a reference to the created request$")
    public void theRequestingClientReceivesBackAReferenceToTheCreatedRequest() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(RequestStatus.PENDING.toString()));
        ObjectMapper mapper = new ObjectMapper();
        RequestDTO informationRequestDTO = mapper.readValue(HttpUtil.lastResponse, RequestDTO.class);
        StorageUtil.storedRequest.setId(informationRequestDTO.getId());
    }

    @And("^the client can ask to view the request$")
    public void theClientCanViewTheRequest() throws Throwable {
        HttpUtil.endpointExtention = "/request/recipient/" + StorageUtil.storedUser.getId();
        String password = DataEncryption.encryptText(StorageUtil.storedUser.getToken(), StorageUtil.storedUser.getPrivateKey());
        HttpUtil.sendGet(new HttpCall(StorageUtil.storedUser.getId(), password));
    }

    @And("^the client receives back a reference to the the request$")
    public void theClientReceivesBackAReferenceToTheTheRequest() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(RequestStatus.PENDING.toString()));
    }

    @When("^the client responds to the request$")
    public void theClientRespondsToTheRequest() throws Throwable {
        HttpUtil.endpointExtention = "/request/" + StorageUtil.storedRequest.getId();
        //Create user to be sent
        StoredRequest storedRequest = new StoredRequest();
        storedRequest.setSenderId(StorageUtil.toRequestUser.getId());
        storedRequest.setReceiverId(StorageUtil.storedUser.getId());
        storedRequest.setIdentityTypeId(StorageUtil.storedIdentityType.getId());
        storedRequest.setFieldsRequested("FIRSTNAME");
        ObjectMapper mapper = new ObjectMapper();
        InformationRequestDTO informationRequestDTO = new InformationRequestDTO();
        informationRequestDTO.setIdentityTypeFields(storedRequest.getFieldsRequested());
        informationRequestDTO.setIdentityTypeValues(StorageUtil.storedUser.getName());
        informationRequestDTO.setCertificateId(StorageUtil.storedSubmission.getCertId());
        informationRequestDTO.setIndentityTypeId(storedRequest.getIdentityTypeId());
        informationRequestDTO.setSenderId(storedRequest.getSenderId());
        informationRequestDTO.setRecipientId(storedRequest.getReceiverId());
        informationRequestDTO.setStatus(RequestStatus.ACCEPTED.toString());
        StorageUtil.storedRequest = storedRequest;
        String password = DataEncryption.encryptText(StorageUtil.storedUser.getToken(), StorageUtil.storedUser.getPrivateKey());
        HttpUtil.sendPut(new HttpCall(mapper.writeValueAsString(informationRequestDTO), StorageUtil.storedUser.getId(), password));
    }

    @Then("^the client receives back a reference to the updated request$")
    public void theClientReceivesBackAReferenceToTheUpdatedRequest() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(RequestStatus.ACCEPTED.toString()));
        ObjectMapper mapper = new ObjectMapper();
        RequestDTO informationRequestDTO = mapper.readValue(HttpUtil.lastResponse, RequestDTO.class);
        StorageUtil.storedRequest.setId(informationRequestDTO.getId());
    }

    @And("^the requesting client can ask to view the update to the request$")
    public void theRequestingClientCanViewTheUpdateToTheRequest() throws Throwable {
        HttpUtil.endpointExtention = "/request/sender/" + StorageUtil.toRequestUser.getId();
        String password = DataEncryption.encryptText(StorageUtil.toRequestUser.getToken(), StorageUtil.toRequestUser.getPrivateKey());
        HttpUtil.sendGet(new HttpCall(StorageUtil.toRequestUser.getId(), password));
    }


    @And("^the requesting client receives back a reference to the the request$")
    public void theRequestingClientReceivesBackAReferenceToTheTheRequest() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(RequestStatus.ACCEPTED.toString()));
    }

    @When("^the requesting client sends a request to another user with a nonexistent identitytype$")
    public void theRequestingClientSendsARequestToAnotherUserWithANonexistentIdentitytype() throws Throwable {
        HttpUtil.endpointExtention = "/request";
        //Create user to be sent
        StoredRequest storedRequest = new StoredRequest();
        storedRequest.setSenderId(StorageUtil.toRequestUser.getId());
        storedRequest.setReceiverId(StorageUtil.storedUser.getId());
        storedRequest.setIdentityTypeId(StorageUtil.storedIdentityType.getId());
        storedRequest.setFieldsRequested("FIRSTNAME");
        ObjectMapper mapper = new ObjectMapper();
        InformationRequestDTO informationRequestDTO = new InformationRequestDTO();
        informationRequestDTO.setIdentityTypeFields(storedRequest.getFieldsRequested());
        informationRequestDTO.setIndentityTypeId(storedRequest.getFieldsRequested());
        informationRequestDTO.setSenderId(storedRequest.getSenderId());
        informationRequestDTO.setRecipientId(storedRequest.getReceiverId());
        StorageUtil.storedRequest = storedRequest;
        String password = DataEncryption.encryptText(StorageUtil.toRequestUser.getToken(), StorageUtil.toRequestUser.getPrivateKey());
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(informationRequestDTO), StorageUtil.toRequestUser.getId(), password));
    }

    @When("^the requesting client sends a request to another user with incorrect identity fields$")
    public void theRequestingClientSendsARequestToAnotherUserWithIncorrectIdentityFields() throws Throwable {
        HttpUtil.endpointExtention = "/request";
        //Create user to be sent
        StoredRequest storedRequest = new StoredRequest();
        storedRequest.setSenderId(StorageUtil.toRequestUser.getId());
        storedRequest.setReceiverId(StorageUtil.storedUser.getId());
        storedRequest.setIdentityTypeId(StorageUtil.storedIdentityType.getId());
        storedRequest.setFieldsRequested("SURNAME");
        ObjectMapper mapper = new ObjectMapper();
        InformationRequestDTO informationRequestDTO = new InformationRequestDTO();
        informationRequestDTO.setIdentityTypeFields(storedRequest.getFieldsRequested());
        informationRequestDTO.setIndentityTypeId(storedRequest.getIdentityTypeId());
        informationRequestDTO.setSenderId(storedRequest.getSenderId());
        informationRequestDTO.setRecipientId(storedRequest.getReceiverId());
        StorageUtil.storedRequest = storedRequest;
        String password = DataEncryption.encryptText(StorageUtil.toRequestUser.getToken(), StorageUtil.toRequestUser.getPrivateKey());
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(informationRequestDTO), StorageUtil.toRequestUser.getId(), password));
    }

    @When("^the client responds to the request with updated fields$")
    public void theClientRespondsToTheRequestWithUpdatedFields() throws Throwable {
        HttpUtil.endpointExtention = "/request/" + StorageUtil.storedRequest.getId();
        //Create user to be sent
        StoredRequest storedRequest = new StoredRequest();
        storedRequest.setSenderId(StorageUtil.toRequestUser.getId());
        storedRequest.setReceiverId(StorageUtil.storedUser.getId());
        storedRequest.setIdentityTypeId(StorageUtil.storedIdentityType.getId());
        storedRequest.setFieldsRequested("SURNAME");
        ObjectMapper mapper = new ObjectMapper();
        InformationRequestDTO informationRequestDTO = new InformationRequestDTO();
        informationRequestDTO.setIdentityTypeFields(storedRequest.getFieldsRequested());
        informationRequestDTO.setIdentityTypeValues(StorageUtil.storedUser.getName());
        informationRequestDTO.setCertificateId(StorageUtil.storedSubmission.getCertId());
        informationRequestDTO.setIndentityTypeId(storedRequest.getIdentityTypeId());
        informationRequestDTO.setSenderId(storedRequest.getSenderId());
        informationRequestDTO.setRecipientId(storedRequest.getReceiverId());
        informationRequestDTO.setStatus(RequestStatus.ACCEPTED.toString());
        StorageUtil.storedRequest = storedRequest;
        String password = DataEncryption.encryptText(StorageUtil.storedUser.getToken(), StorageUtil.storedUser.getPrivateKey());
        HttpUtil.sendPut(new HttpCall(mapper.writeValueAsString(informationRequestDTO), StorageUtil.storedUser.getId(), password));
    }

    @When("^the requesting client responds to the request$")
    public void theRequestingClientRespondsToTheRequest() throws Throwable {
        HttpUtil.endpointExtention = "/request/" + StorageUtil.storedRequest.getId();
        //Create user to be sent
        StoredRequest storedRequest = new StoredRequest();
        storedRequest.setSenderId(StorageUtil.toRequestUser.getId());
        storedRequest.setReceiverId(StorageUtil.storedUser.getId());
        storedRequest.setIdentityTypeId(StorageUtil.storedIdentityType.getId());
        storedRequest.setFieldsRequested("FIRSTNAME");
        ObjectMapper mapper = new ObjectMapper();
        InformationRequestDTO informationRequestDTO = new InformationRequestDTO();
        informationRequestDTO.setIdentityTypeFields(storedRequest.getFieldsRequested());
        informationRequestDTO.setIdentityTypeValues(StorageUtil.storedUser.getName());
        informationRequestDTO.setCertificateId(StorageUtil.storedSubmission.getCertId());
        informationRequestDTO.setIndentityTypeId(storedRequest.getIdentityTypeId());
        informationRequestDTO.setSenderId(storedRequest.getSenderId());
        informationRequestDTO.setRecipientId(storedRequest.getReceiverId());
        informationRequestDTO.setStatus(RequestStatus.ACCEPTED.toString());
        StorageUtil.storedRequest = storedRequest;
        String password = DataEncryption.encryptText(StorageUtil.toRequestUser.getToken(), StorageUtil.toRequestUser.getPrivateKey());
        HttpUtil.sendPut(new HttpCall(mapper.writeValueAsString(informationRequestDTO), StorageUtil.toRequestUser.getId(), password));
    }

    @When("^the client responds to the request to the request with no certificate$")
    public void theClientRespondsToTheRequestToTheRequestWithNoCertificate() throws Throwable {
        HttpUtil.endpointExtention = "/request/" + StorageUtil.storedRequest.getId();
        //Create user to be sent
        StoredRequest storedRequest = new StoredRequest();
        storedRequest.setSenderId(StorageUtil.toRequestUser.getId());
        storedRequest.setReceiverId(StorageUtil.storedUser.getId());
        storedRequest.setIdentityTypeId(StorageUtil.storedIdentityType.getId());
        storedRequest.setFieldsRequested("FIRSTNAME");
        ObjectMapper mapper = new ObjectMapper();
        InformationRequestDTO informationRequestDTO = new InformationRequestDTO();
        informationRequestDTO.setIdentityTypeFields(storedRequest.getFieldsRequested());
        informationRequestDTO.setIdentityTypeValues(StorageUtil.storedUser.getName());
        informationRequestDTO.setCertificateId(StorageUtil.storedSubmission.getId());
        informationRequestDTO.setIndentityTypeId(storedRequest.getIdentityTypeId());
        informationRequestDTO.setSenderId(storedRequest.getSenderId());
        informationRequestDTO.setRecipientId(storedRequest.getReceiverId());
        informationRequestDTO.setStatus(RequestStatus.ACCEPTED.toString());
        StorageUtil.storedRequest = storedRequest;
        String password = DataEncryption.encryptText(StorageUtil.storedUser.getToken(), StorageUtil.storedUser.getPrivateKey());
        HttpUtil.sendPut(new HttpCall(mapper.writeValueAsString(informationRequestDTO), StorageUtil.storedUser.getId(), password));
    }
}
