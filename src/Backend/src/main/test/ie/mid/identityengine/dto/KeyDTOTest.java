package ie.mid.identityengine.dto;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;

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
        assertEquals(key.getId(), ID);
    }

    @Test
    public void setId() throws Exception {
        key.setId(NEW_ID);
        assertEquals(key.getId(), NEW_ID);
        key.setId(ID);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(key.getKeyStatus(), STATUS);
    }

    @Test
    public void setStatus() throws Exception {
        key.setKeyStatus(NEW_STATUS);
        assertEquals(key.getKeyStatus(), NEW_STATUS);
        key.setKeyStatus(STATUS);
    }

    @Test
    public void getKey() throws Exception {
        assertEquals(key.getPublicKey(), KEY);
    }

    @Test
    public void setKey() throws Exception {
        key.setPublicKey(NEW_KEY);
        assertEquals(key.getPublicKey(), NEW_KEY);
        key.setPublicKey(KEY);
    }

    @Test
    public void getUserId() throws Exception {
        assertEquals(key.getUserId(), ID);
    }

    @Test
    public void setUserId() throws Exception {
        key.setUserId(NEW_ID);
        assertEquals(key.getUserId(), NEW_ID);
        key.setUserId(ID);
    }
}