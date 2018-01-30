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
        assertEquals(request.getId(), ID);
    }

    @Test
    public void setId() throws Exception {
        request.setId(NEW_ID);
        assertEquals(request.getId(), NEW_ID);
        request.setId(ID);

    }

    @Test
    public void getSender() throws Exception {
        assertEquals(request.getSender(), ID);
    }

    @Test
    public void setSender() throws Exception {
        request.setSender(NEW_ID);
        assertEquals(request.getSender(), NEW_ID);
        request.setSender(ID);
    }

    @Test
    public void getRecipient() throws Exception {
        assertEquals(request.getRecipient(), ID);
    }

    @Test
    public void setRecipient() throws Exception {
        request.setRecipient(NEW_ID);
        assertEquals(request.getRecipient(), NEW_ID);
        request.setRecipient(ID);
    }

    @Test
    public void getIndentityTypeId() throws Exception {
        assertEquals(request.getIndentityTypeId(), ID);
    }

    @Test
    public void setIndentityTypeId() throws Exception {
        request.setIndentityTypeId(NEW_ID);
        assertEquals(request.getIndentityTypeId(), NEW_ID);
        request.setIndentityTypeId(ID);
    }

    @Test
    public void getIdentityTypeFields() throws Exception {
        assertEquals(request.getIdentityTypeFields(), FIELDS);
    }

    @Test
    public void setIdentityTypeFields() throws Exception {
        request.setIdentityTypeFields(NEW_ID);
        assertEquals(request.getIdentityTypeFields(), NEW_ID);
        request.setIdentityTypeFields(ID);
    }

    @Test
    public void getUserResponse() throws Exception {
        assertEquals(request.getUserResponse(), FIELDS);
    }

    @Test
    public void setUserResponse() throws Exception {
        request.setUserResponse(NEW_FIELDS);
        assertEquals(request.getUserResponse(), NEW_FIELDS);
        request.setUserResponse(FIELDS);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(request.getStatus(), STATUS);
    }

    @Test
    public void setStatus() throws Exception {
        request.setStatus(NEW_STATUS);
        assertEquals(request.getStatus(), NEW_STATUS);
        request.setStatus(STATUS);
    }

    @Test
    public void getCreatedAt() throws Exception {
        assertEquals(request.getCreatedAt(), DATE);
    }

    @Test
    public void setCreatedAt() throws Exception {
        request.setCreatedAt(NEW_DATE);
        assertEquals(request.getCreatedAt(), NEW_DATE);
        request.setCreatedAt(DATE);
    }

    @Test
    public void getUpdatedAt() throws Exception {
        assertEquals(request.getUpdatedAt(), DATE);
    }

    @Test
    public void setUpdatedAt() throws Exception {
        request.setUpdatedAt(NEW_DATE);
        assertEquals(request.getUpdatedAt(), NEW_DATE);
        request.setUpdatedAt(DATE);
    }

}