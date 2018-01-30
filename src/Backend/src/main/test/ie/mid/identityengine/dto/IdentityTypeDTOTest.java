package ie.mid.identityengine.dto;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class IdentityTypeDTOTest {

    @InjectMocks
    private IdentityTypeDTO identityType;

    private static final String ID = "id";
    private static final String NEW_ID = "new_id";
    private static final String FIELDS = "field1,field2";
    private static final String STATUS = "status";
    private static final String NEW_STATUS = "new_status";
    private static final String NEW_FIELDS = "field3,field4";
    private static final Date DATE = new Date();
    private static final Date NEW_DATE = new Date();

    @Before
    public void setUp() throws Exception {
        identityType = new IdentityTypeDTO();
        identityType.setId(ID);
        identityType.setPartyId(ID);
        identityType.setFields(FIELDS);
        identityType.setVersionNumber(1);
        identityType.setStatus(STATUS);

    }

    @Test
    public void getId() throws Exception {
        assertEquals(identityType.getId(), ID);
    }

    @Test
    public void setId() throws Exception {
        identityType.setId(NEW_ID);
        assertEquals(identityType.getId(), NEW_ID);
        identityType.setId(ID);
    }

    @Test
    public void getPartyId() throws Exception {
        assertEquals(identityType.getPartyId(), ID);
    }

    @Test
    public void setPartyId() throws Exception {
        identityType.setPartyId(NEW_ID);
        assertEquals(identityType.getPartyId(), NEW_ID);
        identityType.setPartyId(ID);
    }

    @Test
    public void getFields() throws Exception {
    }

    @Test
    public void setFields() throws Exception {
        identityType.setFields(NEW_FIELDS);
        assertEquals(identityType.getFields(), NEW_FIELDS);
        identityType.setFields(FIELDS);
    }

    @Test
    public void getVersionNumber() throws Exception {
        assertEquals(identityType.getVersionNumber(), 1);
    }

    @Test
    public void setVersionNumber() throws Exception {
        identityType.setVersionNumber(2);
        assertEquals(identityType.getVersionNumber(), 2);
        identityType.setVersionNumber(1);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(identityType.getStatus(), STATUS);
    }

    @Test
    public void setStatus() throws Exception {
        identityType.setStatus(NEW_STATUS);
        assertEquals(identityType.getStatus(), NEW_STATUS);
        identityType.setStatus(STATUS);
    }
}