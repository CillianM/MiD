package ie.mid.identityengine.model;

import ie.mid.identityengine.category.UnitTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(UnitTests.class)
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
        assertEquals(ID, identityType.getId());
    }

    @Test
    public void setId() throws Exception {
        identityType.setId(NEW_ID);
        assertEquals(NEW_ID, identityType.getId());
        identityType.setId(ID);
    }

    @Test
    public void getPartyId() throws Exception {
        assertEquals(ID, identityType.getPartyId());
    }

    @Test
    public void setPartyId() throws Exception {
        identityType.setPartyId(NEW_ID);
        assertEquals(NEW_ID, identityType.getPartyId());
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
        assertEquals(NEW_FIELDS, identityType.getFields());
        identityType.setFields(FIELDS);
    }

    @Test
    public void getVersionNumber() throws Exception {
        assertEquals(1, identityType.getVersionNumber());
    }

    @Test
    public void setVersionNumber() throws Exception {
        identityType.setVersionNumber(2);
        assertEquals(2, identityType.getVersionNumber());
        identityType.setVersionNumber(1);
    }

    @Test
    public void getCreatedAt() throws Exception {
        assertEquals(DATE, identityType.getCreatedAt());
    }

    @Test
    public void setCreatedAt() throws Exception {
        identityType.setCreatedAt(NEW_DATE);
        assertEquals(NEW_DATE, identityType.getCreatedAt());
        identityType.setCreatedAt(DATE);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(STATUS, identityType.getStatus());
    }

    @Test
    public void setStatus() throws Exception {
        identityType.setStatus(NEW_STATUS);
        assertEquals(NEW_STATUS, identityType.getStatus());
        identityType.setStatus(STATUS);
    }

    @Test
    public void getUpdatedAt() throws Exception {
        assertEquals(DATE, identityType.getUpdatedAt());
    }

    @Test
    public void setUpdatedAt() throws Exception {
        identityType.setUpdatedAt(NEW_DATE);
        assertEquals(NEW_DATE, identityType.getUpdatedAt());
        identityType.setUpdatedAt(DATE);
    }

    @Test
    public void getName() {
        assertEquals(NAME, identityType.getName());
    }

    @Test
    public void setName() {
        identityType.setName(NEW_NAME);
        assertEquals(NEW_NAME, identityType.getName());
        identityType.setName(NAME);
    }

    @Test
    public void getIconImg() {
        assertEquals(IMG, identityType.getIconImg());
    }

    @Test
    public void setIconImg() {
        identityType.setIconImg(NEW_IMG);
        assertEquals(NEW_IMG, identityType.getIconImg());
        identityType.setStatus(IMG);
    }

    @Test
    public void getCoverImg() {
        assertEquals(IMG, identityType.getCoverImg());
    }

    @Test
    public void setCoverImg() {
        identityType.setCoverImg(NEW_IMG);
        assertEquals(NEW_IMG, identityType.getCoverImg());
        identityType.setCoverImg(IMG);
    }

}