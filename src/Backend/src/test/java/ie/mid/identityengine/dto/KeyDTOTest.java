package ie.mid.identityengine.dto;

import ie.mid.identityengine.category.UnitTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;

@Category(UnitTests.class)
public class KeyDTOTest {

    @InjectMocks
    private KeyDTO key;

    private static final String ID = "id";
    private static final String NEW_ID = "new_id";
    private static final String KEY = "key";
    private static final String NEW_KEY = "new_key";
    private static final String STATUS = "status";
    private static final String NEW_STATUS = "new_status";

    @Before
    public void setUp() throws Exception {
        key = new KeyDTO();
        key = new KeyDTO(ID, ID, KEY, STATUS);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(ID, key.getId());
    }

    @Test
    public void setId() throws Exception {
        key.setId(NEW_ID);
        assertEquals(NEW_ID, key.getId());
        key.setId(ID);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(STATUS, key.getKeyStatus());
    }

    @Test
    public void setStatus() throws Exception {
        key.setKeyStatus(NEW_STATUS);
        assertEquals(NEW_STATUS, key.getKeyStatus());
        key.setKeyStatus(STATUS);
    }

    @Test
    public void getKey() throws Exception {
        assertEquals(KEY, key.getPublicKey());
    }

    @Test
    public void setKey() throws Exception {
        key.setPublicKey(NEW_KEY);
        assertEquals(NEW_KEY, key.getPublicKey());
        key.setPublicKey(KEY);
    }

    @Test
    public void getUserId() throws Exception {
        assertEquals(ID, key.getUserId());
    }

    @Test
    public void setUserId() throws Exception {
        key.setUserId(NEW_ID);
        assertEquals(NEW_ID, key.getUserId());
        key.setUserId(ID);
    }
}