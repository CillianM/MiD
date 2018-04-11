package ie.mid.identityengine.dto;

import ie.mid.identityengine.category.UnitTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;

@Category(UnitTests.class)
public class PartyDTOTest {

    @InjectMocks
    private PartyDTO party;

    private static final String ID = "id";
    private static final String NEW_ID = "new_id";
    private static final String NAME = "name";
    private static final String NEW_NAME = "new_name";
    private static final String STATUS = "status";
    private static final String NEW_STATUS = "new_status";

    @Before
    public void setUp() throws Exception {
        party = new PartyDTO();
        party.setId(ID);
        party.setName(NAME);
        party.setStatus(STATUS);
        party.setKeyId(ID);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(ID, party.getId());
    }

    @Test
    public void setId() throws Exception {
        party.setId(NEW_ID);
        assertEquals(NEW_ID, party.getId());
        party.setId(ID);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(NAME, party.getName());
    }

    @Test
    public void setName() throws Exception {
        party.setName(NEW_NAME);
        assertEquals(NEW_NAME, party.getName());
        party.setName(ID);
    }

    @Test
    public void getKeyId() throws Exception {
        assertEquals(ID, party.getKeyId());
    }

    @Test
    public void setKeyId() throws Exception {
        party.setKeyId(NEW_ID);
        assertEquals(NEW_ID, party.getKeyId());
        party.setKeyId(ID);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(STATUS, party.getStatus());
    }

    @Test
    public void setStatus() throws Exception {
        party.setStatus(NEW_STATUS);
        assertEquals(NEW_STATUS, party.getStatus());
        party.setStatus(STATUS);
    }
}