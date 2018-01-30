package ie.mid.identityengine.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PartyTest {

    @InjectMocks
    private Party party;

    private static final String ID = "id";
    private static final String NEW_ID = "new_id";
    private static final String NAME = "name";
    private static final String NEW_NAME = "new_name";
    private static final String STATUS = "status";
    private static final String NEW_STATUS = "new_status";
    private static final Date DATE = new Date();
    private static final Date NEW_DATE = new Date();

    @Before
    public void setUp() throws Exception {
        party = new Party();
        party.setId(ID);
        party.setName(NAME);
        party.setStatus(STATUS);
        party.setCreatedAt(DATE);
        party.setUpdatedAt(DATE);
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

    @Test
    public void getCreatedAt() throws Exception {
        assertEquals(party.getCreatedAt(), DATE);
    }

    @Test
    public void setCreatedAt() throws Exception {
        party.setCreatedAt(NEW_DATE);
        assertEquals(party.getCreatedAt(), NEW_DATE);
        party.setCreatedAt(DATE);
    }

    @Test
    public void getUpdatedAt() throws Exception {
        assertEquals(party.getUpdatedAt(), DATE);
    }

    @Test
    public void setUpdatedAt() throws Exception {
        party.setUpdatedAt(NEW_DATE);
        assertEquals(party.getUpdatedAt(), NEW_DATE);
        party.setUpdatedAt(DATE);
    }

}