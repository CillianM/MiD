package ie.mid.identityengine.dto;

import ie.mid.identityengine.enums.FieldType;
import ie.mid.identityengine.model.Field;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private static final String NAME = "name";
    private static final String NEW_NAME = "new_name";
    private static final String IMG = "img";
    private static final String NEW_IMG = "new_img";
    private Field field = new Field(NAME, FieldType.ADDRESS);
    private List<Field> fieldList = new ArrayList<>();
    private Field newField = new Field(NEW_NAME, FieldType.ADDRESS);
    private List<Field> newFieldList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        fieldList.add(field);
        newFieldList.add(newField);
        fieldList.add(field);
        identityType = new IdentityTypeDTO();
        identityType.setId(ID);
        identityType.setPartyId(ID);
        identityType.setFields(fieldList);
        identityType.setVersionNumber(1);
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
        assertEquals(identityType.getFields().get(0).getName(), NAME);
    }

    @Test
    public void setFields() throws Exception {
        identityType.setFields(newFieldList);
        assertEquals(identityType.getFields().get(0).getName(), NEW_NAME);
        identityType.setFields(fieldList);
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