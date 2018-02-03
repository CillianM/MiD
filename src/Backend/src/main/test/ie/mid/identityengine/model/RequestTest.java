package ie.mid.identityengine.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RequestTest {

    @InjectMocks
    private Request request;

    private static final String ID = "id";
    private static final String NEW_ID = "new_id";
    private static final String FIELDS = "field1,field2";
    private static final String NEW_FIELDS = "field3,field4";
    private static final String STATUS = "status";
    private static final String NEW_STATUS = "new_status";
    private static final Date DATE = new Date();
    private static final Date NEW_DATE = new Date();

    @Before
    public void setUp() throws Exception {
        request = new Request();
        request.setId(ID);
        request.setSender(ID);
        request.setRecipient(ID);
        request.setIndentityTypeId(ID);
        request.setIdentityTypeFields(FIELDS);
        request.setUserResponse(FIELDS);
        request.setStatus(STATUS);
        request.setCreatedAt(DATE);
        request.setUpdatedAt(DATE);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(ID, request.getId());
    }

    @Test
    public void setId() throws Exception {
        request.setId(NEW_ID);
        assertEquals(NEW_ID, request.getId());
        request.setId(ID);

    }

    @Test
    public void getSender() throws Exception {
        assertEquals(ID, request.getSender());
    }

    @Test
    public void setSender() throws Exception {
        request.setSender(NEW_ID);
        assertEquals(NEW_ID, request.getSender());
        request.setSender(ID);
    }

    @Test
    public void getRecipient() throws Exception {
        assertEquals(ID, request.getRecipient());
    }

    @Test
    public void setRecipient() throws Exception {
        request.setRecipient(NEW_ID);
        assertEquals(NEW_ID, request.getRecipient());
        request.setRecipient(ID);
    }

    @Test
    public void getIndentityTypeId() throws Exception {
        assertEquals(ID, request.getIndentityTypeId());
    }

    @Test
    public void setIndentityTypeId() throws Exception {
        request.setIndentityTypeId(NEW_ID);
        assertEquals(NEW_ID, request.getIndentityTypeId());
        request.setIndentityTypeId(ID);
    }

    @Test
    public void getIdentityTypeFields() throws Exception {
        assertEquals(FIELDS, request.getIdentityTypeFields());
    }

    @Test
    public void setIdentityTypeFields() throws Exception {
        request.setIdentityTypeFields(NEW_ID);
        assertEquals(NEW_ID, request.getIdentityTypeFields());
        request.setIdentityTypeFields(ID);
    }

    @Test
    public void getUserResponse() throws Exception {
        assertEquals(FIELDS, request.getUserResponse());
    }

    @Test
    public void setUserResponse() throws Exception {
        request.setUserResponse(NEW_FIELDS);
        assertEquals(NEW_FIELDS, request.getUserResponse());
        request.setUserResponse(FIELDS);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(STATUS, request.getStatus());
    }

    @Test
    public void setStatus() throws Exception {
        request.setStatus(NEW_STATUS);
        assertEquals(NEW_STATUS, request.getStatus());
        request.setStatus(STATUS);
    }

    @Test
    public void getCreatedAt() throws Exception {
        assertEquals(DATE, request.getCreatedAt());
    }

    @Test
    public void setCreatedAt() throws Exception {
        request.setCreatedAt(NEW_DATE);
        assertEquals(NEW_DATE, request.getCreatedAt());
        request.setCreatedAt(DATE);
    }

    @Test
    public void getUpdatedAt() throws Exception {
        assertEquals(DATE, request.getUpdatedAt());
    }

    @Test
    public void setUpdatedAt() throws Exception {
        request.setUpdatedAt(NEW_DATE);
        assertEquals(NEW_DATE, request.getUpdatedAt());
        request.setUpdatedAt(DATE);
    }

}