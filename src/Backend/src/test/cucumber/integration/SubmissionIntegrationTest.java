package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import ie.mid.identityengine.dto.SubmissionDTO;
import ie.mid.identityengine.enums.RequestStatus;
import ie.mid.identityengine.security.DataEncryption;
import integration.model.HttpCall;
import integration.model.StoredSubmission;
import integration.util.HttpUtil;
import integration.util.StorageUtil;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features")
public class SubmissionIntegrationTest {

    private static final String SUBMISSION_DATA = "SUBMISSION_DATA";

    @When("^the client calls /submission for an existing party$")
    public void theClientCallsSubmissionForAnExistingParty() throws Throwable {
        HttpUtil.endpointExtention = "/submission";
        StoredSubmission storedSubmission = new StoredSubmission();
        storedSubmission.setPartyId(StorageUtil.storedParty.getId());
        storedSubmission.setUserId(StorageUtil.storedUser.getId());
        storedSubmission.setData(SUBMISSION_DATA);
        ObjectMapper mapper = new ObjectMapper();
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setPartyId(storedSubmission.getPartyId());
        submissionDTO.setUserId(storedSubmission.getUserId());
        submissionDTO.setData(storedSubmission.getData());
        StorageUtil.storedSubmission = storedSubmission;
        String password = DataEncryption.encryptText(StorageUtil.storedUser.getToken(), StorageUtil.storedUser.getPrivateKey());
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(submissionDTO), StorageUtil.storedUser.getId(), password));
    }

    @And("^the client receives back a reference to the created submission$")
    public void theClientReceivesBackAReferenceToTheCreatedSubmission() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(RequestStatus.PENDING.toString()));
        ObjectMapper mapper = new ObjectMapper();
        SubmissionDTO submissionDTO = mapper.readValue(HttpUtil.lastResponse, SubmissionDTO.class);
        StorageUtil.storedSubmission.setId(submissionDTO.getId());
    }


    @When("^the client calls /submission for an nonexistent party$")
    public void theClientCallsSubmissionForAnNonexistentParty() throws Throwable {
        HttpUtil.endpointExtention = "/submission";
        StoredSubmission storedSubmission = new StoredSubmission();
        storedSubmission.setPartyId(StorageUtil.storedParty.getId());
        storedSubmission.setUserId(StorageUtil.storedUser.getId());
        storedSubmission.setData(SUBMISSION_DATA);
        ObjectMapper mapper = new ObjectMapper();
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setPartyId(storedSubmission.getUserId());
        submissionDTO.setUserId(storedSubmission.getUserId());
        submissionDTO.setData(storedSubmission.getData());
        StorageUtil.storedSubmission = storedSubmission;
        String password = DataEncryption.encryptText(StorageUtil.storedUser.getToken(), StorageUtil.storedUser.getPrivateKey());
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(submissionDTO), StorageUtil.storedUser.getId(), password));
    }

    @When("^the client calls /submission using incorrect auth headers$")
    public void theClientCallsSubmissionUsingIncorrectAuthHeaders() throws Throwable {
        HttpUtil.endpointExtention = "/submission";
        StoredSubmission storedSubmission = new StoredSubmission();
        storedSubmission.setPartyId(StorageUtil.storedParty.getId());
        storedSubmission.setUserId(StorageUtil.storedUser.getId());
        storedSubmission.setData(SUBMISSION_DATA);
        ObjectMapper mapper = new ObjectMapper();
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setPartyId(storedSubmission.getUserId());
        submissionDTO.setUserId(storedSubmission.getUserId());
        submissionDTO.setData(storedSubmission.getData());
        StorageUtil.storedSubmission = storedSubmission;
        String password = StorageUtil.storedUser.getId();
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(submissionDTO), StorageUtil.storedUser.getId(), password));
    }

    @When("^the client calls /submission to update an existing submission as a party$")
    public void theClientCallsSubmissionToUpdateAnExistingSubmissionAsAParty() throws Throwable {
        HttpUtil.endpointExtention = "/submission" + "/" + StorageUtil.storedSubmission.getId();
        ObjectMapper mapper = new ObjectMapper();
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setPartyId(StorageUtil.storedSubmission.getPartyId());
        submissionDTO.setUserId(StorageUtil.storedSubmission.getUserId());
        submissionDTO.setData(StorageUtil.storedSubmission.getData());
        submissionDTO.setStatus(RequestStatus.ACCEPTED.toString());
        String password = DataEncryption.encryptText(StorageUtil.storedParty.getToken(), StorageUtil.storedParty.getPrivateKey());
        HttpUtil.sendPut(new HttpCall(mapper.writeValueAsString(submissionDTO), StorageUtil.storedParty.getId(), password));
    }

    @And("^the client receives back a reference to the updated submission$")
    public void theClientReceivesBackAReferenceToTheUpdatedSubmission() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(RequestStatus.ACCEPTED.toString()));
        ObjectMapper mapper = new ObjectMapper();
        SubmissionDTO submissionDTO = mapper.readValue(HttpUtil.lastResponse, SubmissionDTO.class);
        StorageUtil.storedSubmission.setId(submissionDTO.getId());
        StorageUtil.storedSubmission.setCertId(submissionDTO.getCertId());
    }

    @When("^the client calls /submission to update an existing submission as a user$")
    public void theClientCallsSubmissionToUpdateAnExistingSubmissionAsAUser() throws Throwable {
        HttpUtil.endpointExtention = "/submission" + "/" + StorageUtil.storedSubmission.getId();
        ObjectMapper mapper = new ObjectMapper();
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setPartyId(StorageUtil.storedSubmission.getPartyId());
        submissionDTO.setUserId(StorageUtil.storedSubmission.getUserId());
        submissionDTO.setData(StorageUtil.storedSubmission.getData());
        submissionDTO.setStatus(RequestStatus.ACCEPTED.toString());
        String password = DataEncryption.encryptText(StorageUtil.storedUser.getToken(), StorageUtil.storedUser.getPrivateKey());
        HttpUtil.sendPut(new HttpCall(mapper.writeValueAsString(submissionDTO), StorageUtil.storedUser.getId(), password));
    }

    @When("^the client calls /submission to get current status of their submission$")
    public void theClientCallsSubmissionToGetCurrentStatusOfTheirSubmission() throws Throwable {
        HttpUtil.endpointExtention = "/submission" + "/" + StorageUtil.storedSubmission.getId();
        String password = DataEncryption.encryptText(StorageUtil.storedUser.getToken(), StorageUtil.storedUser.getPrivateKey());
        HttpUtil.sendGet(new HttpCall(StorageUtil.storedUser.getId(), password));
    }

    @And("^the client receives back a reference to the current submission$")
    public void theClientReceivesBackAReferenceToTheCurrentSubmission() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(RequestStatus.PENDING.toString()));
    }

    @When("^the client calls /submission to get current status of a submission thats not theirs$")
    public void theClientCallsSubmissionToGetCurrentStatusOfASubmissionThatsNotTheirs() throws Throwable {
        HttpUtil.endpointExtention = "/submission" + "/" + StorageUtil.storedSubmission.getId();
        String password = DataEncryption.encryptText(StorageUtil.storedUser.getToken(), StorageUtil.storedUser.getPrivateKey());
        HttpUtil.sendGet(new HttpCall(StorageUtil.storedUser.getId(), password));
    }
}
