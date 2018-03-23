package ie.mid.identityengine;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import ie.mid.identityengine.dto.UserDTO;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class UserIntegrationTest {

    private static final String FCM_TOKEN = "FCM_TOKEN";
    private static final String PUBLIC_KEY = "PUBLIC_KEY";
    private static final String NAME = "NAME";


    @When("^the client calls /user with user data$")
    public void clientSendsPostRequestToUser() throws Throwable {
        HttpUtil.endpointExtention = "/user";
        //Create user to be sent
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userToCreate = new UserDTO();
        userToCreate.setFcmToken(FCM_TOKEN);
        userToCreate.setPublicKey(PUBLIC_KEY);
        userToCreate.setNickname(NAME);
        HttpUtil.sendPost(mapper.writeValueAsString(userToCreate));
    }

    @When("^the client calls /user with no user data$")
    public void clientSendsEmptyPostRequestToUser() throws Throwable {
        HttpUtil.endpointExtention = "/user";
        //Create user to be sent
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userToCreate = new UserDTO();
        HttpUtil.sendPost(mapper.writeValueAsString(userToCreate));
    }

    @Then("^the client receives status code of (\\d+)$")
    public void clientReceivesStatusCodeOf(int statusCode) throws Throwable {
        assertEquals(statusCode, HttpUtil.latestResponseCode);
    }

    @And("^the client receives back a reference to the created user$")
    public void clientReceivesBackResponseFromServer() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(FCM_TOKEN));
    }
}
