package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import ie.mid.identityengine.dto.NewUserDTO;
import ie.mid.identityengine.dto.UserDTO;
import ie.mid.identityengine.security.DataEncryption;
import ie.mid.identityengine.security.KeyUtil;
import integration.model.HttpCall;
import integration.model.StoredUser;
import integration.util.HttpUtil;
import integration.util.StorageUtil;
import org.junit.runner.RunWith;

import java.security.KeyPair;

import static org.junit.Assert.*;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features")
public class UserIntegrationTest {

    private static final String FCM_TOKEN = "FCM_TOKEN";
    private static final String NEW_FCM_TOKEN = "NEW_FCM_TOKEN";
    private static final String PUBLIC_KEY = "PUBLIC_KEY";
    private static final String NAME = "TEST_USER";


    @When("^the client calls /user with user data$")
    public void clientSendsPostRequestToUser() throws Throwable {
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
        StorageUtil.storedUser = user;
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(userToCreate)));
    }

    @When("^the client calls /user with no user data$")
    public void clientSendsEmptyPostRequestToUser() throws Throwable {
        HttpUtil.endpointExtention = "/user";
        //Create user to be sent
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userToCreate = new UserDTO();
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(userToCreate)));
    }

    @When("^the client calls /user with incorrect user key$")
    public void clientSendsIncorrectlyFormattedKey() throws Throwable {
        HttpUtil.endpointExtention = "/user";
        //Create user to be sent
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userToCreate = new UserDTO();
        userToCreate.setFcmToken(FCM_TOKEN);
        userToCreate.setPublicKey(PUBLIC_KEY);
        userToCreate.setNickname(NAME);
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(userToCreate)));
    }

    @Then("^the client receives status code of (\\d+)$")
    public void clientReceivesStatusCodeOf(int statusCode) throws Throwable {
        assertEquals(statusCode, HttpUtil.lastReponseCode);
    }

    @And("^the client receives back a reference to the created user$")
    public void clientReceivesBackResponseFromServer() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(FCM_TOKEN));
        ObjectMapper mapper = new ObjectMapper();
        NewUserDTO newUserDTO = mapper.readValue(HttpUtil.lastResponse, NewUserDTO.class);
        StorageUtil.storedUser.setToken(newUserDTO.getUserToken());
        StorageUtil.storedUser.setId(newUserDTO.getId());
    }

    @When("^the client calls /user with existing userId$")
    public void theClientCallsUserWithExistingUserId() throws Throwable {
        HttpUtil.endpointExtention = "/user" + "/" + StorageUtil.storedUser.getId();
        HttpUtil.sendGet(new HttpCall());
    }

    @And("^the client receives back data on the referenced user$")
    public void theClientReceivesBackDataOnTheReferencedUser() throws Throwable {
        assertTrue(HttpUtil.lastResponse.contains(StorageUtil.storedUser.getName()));
    }

    @When("^the client calls /user with nonexistent userId$")
    public void theClientCallsUserWithNonexistentUserId() throws Throwable {
        HttpUtil.endpointExtention = "/user" + "/" + NAME;
        HttpUtil.sendGet(new HttpCall());
    }

    @When("^the client calls /user/token with an updated fcmtoken$")
    public void theClientCallsUserTokenWithAnUpdatedFcmtoken() throws Throwable {
        HttpUtil.endpointExtention = "/user" + "/" + StorageUtil.storedUser.getId() + "/token";
        String password = DataEncryption.encryptText(StorageUtil.storedUser.getToken(), StorageUtil.storedUser.getPrivateKey());
        ObjectMapper mapper = new ObjectMapper();
        HttpUtil.sendPut(new HttpCall(mapper.writeValueAsString(NEW_FCM_TOKEN), StorageUtil.storedUser.getId(), password));
    }

    @And("^the client receives back data on the updated user$")
    public void theClientReceivesBackDataOnTheUpdatedUser() throws Throwable {
        assertTrue(HttpUtil.lastResponse.contains(NEW_FCM_TOKEN));
    }

    @When("^the client calls /user/token without correct credentials$")
    public void theClientCallsUserTokenWithoutCorrectCredentials() throws Throwable {
        HttpUtil.endpointExtention = "/user" + "/" + StorageUtil.storedUser.getId() + "/token";
        String password = StorageUtil.storedUser.getId();
        ObjectMapper mapper = new ObjectMapper();
        HttpUtil.sendPut(new HttpCall(mapper.writeValueAsString(NEW_FCM_TOKEN), StorageUtil.storedUser.getId(), password));
    }
}
