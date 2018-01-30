package ie.mid.identityengine.dto;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class RequestDTOTest {
    @InjectMocks
    private RequestDTO request;

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
        request = new RequestDTO();
        request.setId(ID);
        request.setSender(ID);
        request.setRecipient(ID);
        request.setIndentityTypeId(ID);
        request.setIdentityTypeFields(FIELDS);
        request.setIdentityTypeValues(FIELDS);
        request.setStatus(STATUS);
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
    public void getIdentityTypeValues() throws Exception {
        assertEquals(request.getIdentityTypeValues(), FIELDS);
    }

    @Test
    public void setIdentityTypeValues() throws Exception {
        request.setIdentityTypeValues(NEW_ID);
        assertEquals(request.getIdentityTypeValues(), NEW_ID);
        request.setIdentityTypeValues(ID);
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
}