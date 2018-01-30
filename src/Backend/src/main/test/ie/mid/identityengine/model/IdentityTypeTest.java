package ie.mid.identityengine.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class IdentityTypeTest {


    @InjectMocks
    private IdentityType identityType;

    private static final String ID = "id";
    private static final String NEW_ID = "new_id";
    private static final String FIELDS = "field1,field2";
    private static final String STATUS = "status";
    private static final String NEW_STATUS = "new_status";
    private static final String NEW_FIELDS = "field3,field4";
    private static final Date DATE = new Date();
    private static final Date NEW_DATE = new Date();
    private static final String NAME = "name";
    private static final String NEW_NAME = "new_name";
    private static final String IMG = "img";
    private static final String NEW_IMG = "new_img";

    @Before
    public void setUp() throws Exception {
        identityType = new IdentityType();
        identityType.setId(ID);
        identityType.setPartyId(ID);
        identityType.setFields(FIELDS);
        identityType.setVersionNumber(1);
        identityType.setCreatedAt(DATE);
        identityType.setUpdatedAt(DATE);
        identityType.setStatus(STATUS);
        identityType.setName(NAME);
        identityType.setCoverImg(IMG);
        identityType.setIconImg(IMG);

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
    public void getFieldsArray() throws Exception {
        assertTrue(identityType.getFieldsArray().length > 0);
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
    public void getCreatedAt() throws Exception {
        assertEquals(identityType.getCreatedAt(), DATE);
    }

    @Test
    public void setCreatedAt() throws Exception {
        identityType.setCreatedAt(NEW_DATE);
        assertEquals(identityType.getCreatedAt(), NEW_DATE);
        identityType.setCreatedAt(DATE);
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

    @Test
    public void getUpdatedAt() throws Exception {
        assertEquals(identityType.getUpdatedAt(), DATE);
    }

    @Test
    public void setUpdatedAt() throws Exception {
        identityType.setUpdatedAt(NEW_DATE);
        assertEquals(identityType.getUpdatedAt(), NEW_DATE);
        identityType.setUpdatedAt(DATE);
    }

    @Test
    public void getName() {
        assertEquals(identityType.getName(), NAME);
    }

    @Test
    public void setName() {
        identityType.setName(NEW_NAME);
        assertEquals(identityType.getName(), NEW_NAME);
        identityType.setName(NAME);
    }

    @Test
    public void getIconImg() {
        assertEquals(identityType.getIconImg(), IMG);
    }

    @Test
    public void setIconImg() {
        identityType.setIconImg(NEW_IMG);
        assertEquals(identityType.getIconImg(), NEW_IMG);
        identityType.setStatus(IMG);
    }

    @Test
    public void getCoverImg() {
        assertEquals(identityType.getCoverImg(), IMG);
    }

    @Test
    public void setCoverImg() {
        identityType.setCoverImg(NEW_IMG);
        assertEquals(identityType.getCoverImg(), NEW_IMG);
        identityType.setCoverImg(IMG);
    }

}