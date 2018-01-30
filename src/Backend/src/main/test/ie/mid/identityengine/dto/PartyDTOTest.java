package ie.mid.identityengine.dto;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;

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
    }

    @Test
    public void getId() throws Exception {
        assertEquals(party.getId(), ID);
    }

    @Test
    public void setId() throws Exception {
        party.setId(NEW_ID);
        assertEquals(party.getId(), NEW_ID);
        party.setId(ID);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(party.getName(), NAME);
    }

    @Test
    public void setName() throws Exception {
        party.setName(NEW_NAME);
        assertEquals(party.getName(), NEW_NAME);
        party.setName(ID);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(party.getStatus(), STATUS);
    }

    @Test
    public void setStatus() throws Exception {
        party.setStatus(NEW_STATUS);
        assertEquals(party.getStatus(), NEW_STATUS);
        party.setStatus(STATUS);
    }
}