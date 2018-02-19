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
        request.setSenderId(ID);
        request.setRecipientId(ID);
        request.setIndentityTypeId(ID);
        request.setIdentityTypeFields(FIELDS);
        request.setIdentityTypeValues(FIELDS);
        request.setStatus(STATUS);
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
        assertEquals(ID, request.getSenderId());
    }

    @Test
    public void setSender() throws Exception {
        request.setSenderId(NEW_ID);
        assertEquals(NEW_ID, request.getSenderId());
        request.setSenderId(ID);
    }

    @Test
    public void getRecipient() throws Exception {
        assertEquals(ID, request.getRecipientId());
    }

    @Test
    public void setRecipient() throws Exception {
        request.setRecipientId(NEW_ID);
        assertEquals(NEW_ID, request.getRecipientId());
        request.setRecipientId(ID);
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
    public void getIdentityTypeValues() throws Exception {
        assertEquals(FIELDS, request.getIdentityTypeValues());
    }

    @Test
    public void setIdentityTypeValues() throws Exception {
        request.setIdentityTypeValues(NEW_ID);
        assertEquals(NEW_ID, request.getIdentityTypeValues());
        request.setIdentityTypeValues(ID);
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
}