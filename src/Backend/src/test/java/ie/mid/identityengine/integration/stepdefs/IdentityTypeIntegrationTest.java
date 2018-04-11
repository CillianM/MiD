package ie.mid.identityengine.integration.stepdefs;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import ie.mid.identityengine.dto.IdentityTypeDTO;
import ie.mid.identityengine.enums.FieldType;
import ie.mid.identityengine.integration.model.HttpCall;
import ie.mid.identityengine.integration.model.StoredIdentityType;
import ie.mid.identityengine.integration.util.HttpUtil;
import ie.mid.identityengine.integration.util.StorageUtil;
import ie.mid.identityengine.model.Field;
import ie.mid.identityengine.security.DataEncryption;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class IdentityTypeIntegrationTest {

    private static final String NAME = "TEST_IDENTITY_TYPE";
    private static final String URL = "https://www.dfa.ie/media/dfa/alldfawebsitemedia/passportcitizenship/passport-landing-banner.jpg";


    @When("^the client calls /identitytype with identitytype data$")
    public void theClientCallsIdentitytypeWithIdentitytypeData() throws Throwable {
        HttpUtil.endpointExtention = "/identitytype";
        Field field = new Field();
        field.setName("Firstname");
        field.setType(FieldType.FIRSTNAME);
        List<Field> fieldList = new ArrayList<>();
        fieldList.add(field);
        StoredIdentityType storedIdentityType = new StoredIdentityType();
        storedIdentityType.setName(NAME);
        storedIdentityType.setFields(fieldList);
        ObjectMapper mapper = new ObjectMapper();
        IdentityTypeDTO identityTypeToCreate = new IdentityTypeDTO();
        identityTypeToCreate.setName(storedIdentityType.getName());
        identityTypeToCreate.setCoverImg(URL);
        identityTypeToCreate.setIconImg(URL);
        identityTypeToCreate.setPartyId(StorageUtil.storedParty.getId());
        identityTypeToCreate.setFields(fieldList);
        StorageUtil.storedIdentityType = storedIdentityType;
        String password = DataEncryption.encryptText(StorageUtil.storedParty.getToken(), StorageUtil.storedParty.getPrivateKey());
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(identityTypeToCreate), StorageUtil.storedParty.getId(), password));
    }

    @And("^the client receives back a reference to the created identitytype$")
    public void theClientReceivesBackAReferenceToTheCreatedIdentitytype() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(NAME));
        ObjectMapper mapper = new ObjectMapper();
        IdentityTypeDTO identityTypeDTO = mapper.readValue(HttpUtil.lastResponse, IdentityTypeDTO.class);
        StorageUtil.storedIdentityType.setId(identityTypeDTO.getId());
    }

    @When("^the client calls /identitytype with incorrect field types$")
    public void theClientCallsIdentitytypeWithIncorrectFieldTypes() throws Throwable {
        HttpUtil.endpointExtention = "/identitytype";
        Field field = new Field();
        field.setName("Firstname");
        field.setType("NOTAREALTYPE");
        List<Field> fieldList = new ArrayList<>();
        fieldList.add(field);
        ObjectMapper mapper = new ObjectMapper();
        IdentityTypeDTO identityTypeToCreate = new IdentityTypeDTO();
        identityTypeToCreate.setName(NAME);
        identityTypeToCreate.setCoverImg(URL);
        identityTypeToCreate.setIconImg(URL);
        identityTypeToCreate.setPartyId(StorageUtil.storedParty.getId());
        identityTypeToCreate.setFields(fieldList);
        String password = DataEncryption.encryptText(StorageUtil.storedParty.getToken(), StorageUtil.storedParty.getPrivateKey());
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(identityTypeToCreate), StorageUtil.storedParty.getId(), password));
    }

    @When("^the client calls /identitytype with incorrect auth headers$")
    public void theClientCallsIdentitytypeWithIncorrectAuthHeaders() throws Throwable {
        HttpUtil.endpointExtention = "/identitytype";
        ;
        ObjectMapper mapper = new ObjectMapper();
        IdentityTypeDTO identityTypeToCreate = new IdentityTypeDTO();
        String password = StorageUtil.storedParty.getId();
        HttpUtil.sendPost(new HttpCall(mapper.writeValueAsString(identityTypeToCreate), StorageUtil.storedParty.getId(), password));
    }

    @When("^the client calls /identitytype with an updated field type$")
    public void theClientCallsIdentitytypeWithAnUpdatedFieldType() throws Throwable {
        HttpUtil.endpointExtention = "/identitytype" + "/" + StorageUtil.storedIdentityType.getId();
        Field field = new Field();
        field.setName("Birthday");
        field.setType(FieldType.BIRTHDAY);
        List<Field> fieldList = new ArrayList<>();
        fieldList.add(field);
        ObjectMapper mapper = new ObjectMapper();
        IdentityTypeDTO identityTypeToCreate = new IdentityTypeDTO();
        identityTypeToCreate.setName(StorageUtil.storedIdentityType.getName());
        identityTypeToCreate.setCoverImg(URL);
        identityTypeToCreate.setIconImg(URL);
        identityTypeToCreate.setPartyId(StorageUtil.storedParty.getId());
        identityTypeToCreate.setFields(fieldList);
        String password = DataEncryption.encryptText(StorageUtil.storedParty.getToken(), StorageUtil.storedParty.getPrivateKey());
        HttpUtil.sendPut(new HttpCall(mapper.writeValueAsString(identityTypeToCreate), StorageUtil.storedParty.getId(), password));
    }

    @And("^the client receives back a reference to the updated identitytype$")
    public void theClientReceivesBackAReferenceToTheUpdatedIdentitytype() throws Throwable {
        assertNotNull(HttpUtil.lastResponse);
        assertTrue(HttpUtil.lastResponse.contains(NAME));
        ObjectMapper mapper = new ObjectMapper();
        IdentityTypeDTO identityTypeDTO = mapper.readValue(HttpUtil.lastResponse, IdentityTypeDTO.class);
        StorageUtil.storedIdentityType.setFields(identityTypeDTO.getFields());
        assertTrue(identityTypeDTO.getVersionNumber() == 2);
    }

    @When("^the client calls /identitytype with an incorrectly updated field type$")
    public void theClientCallsIdentitytypeWithAnIncorrectlyUpdatedFieldType() throws Throwable {
        HttpUtil.endpointExtention = "/identitytype" + "/" + StorageUtil.storedIdentityType.getId();
        Field field = new Field();
        field.setName("Birthday");
        field.setType("NOTATYPE");
        List<Field> fieldList = new ArrayList<>();
        fieldList.add(field);
        ObjectMapper mapper = new ObjectMapper();
        IdentityTypeDTO identityTypeToCreate = new IdentityTypeDTO();
        identityTypeToCreate.setName(StorageUtil.storedIdentityType.getName());
        identityTypeToCreate.setCoverImg(URL);
        identityTypeToCreate.setIconImg(URL);
        identityTypeToCreate.setPartyId(StorageUtil.storedParty.getId());
        identityTypeToCreate.setFields(fieldList);
        String password = DataEncryption.encryptText(StorageUtil.storedParty.getToken(), StorageUtil.storedParty.getPrivateKey());
        HttpUtil.sendPut(new HttpCall(mapper.writeValueAsString(identityTypeToCreate), StorageUtil.storedParty.getId(), password));
    }
}
