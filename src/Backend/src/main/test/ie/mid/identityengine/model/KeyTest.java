package ie.mid.identityengine.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class KeyTest {

    @InjectMocks
    private Key key;

    private static final String ID = "id";
    private static final String NEW_ID = "new_id";
    private static final String KEY = "key";
    private static final String NEW_KEY = "new_key";
    private static final String KEY_CLASS = "key_class";
    private static final String NEW_KEY_CLASS = "new_key_class";
    private static final String STATUS = "status";
    private static final String NEW_STATUS = "new_status";
    private static final Date DATE = new Date();
    private static final Date NEW_DATE = new Date();

    @Before
    public void setUp() throws Exception {
        key = new Key();
        key.setId(ID);
        key.setUserId(ID);
        key.setKey(KEY);
        key.setKeyClass(KEY_CLASS);
        key.setStatus(STATUS);
        key.setValidUntil(DATE);
        key.setCreatedAt(DATE);
        key.setUpdatedAt(DATE);
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
        assertEquals(key.getStatus(), STATUS);
    }

    @Test
    public void setStatus() throws Exception {
        key.setStatus(NEW_STATUS);
        assertEquals(key.getStatus(), NEW_STATUS);
        key.setStatus(STATUS);
    }

    @Test
    public void getCreatedAt() throws Exception {
        assertEquals(key.getCreatedAt(), DATE);
    }

    @Test
    public void setCreatedAt() throws Exception {
        key.setCreatedAt(NEW_DATE);
        assertEquals(key.getCreatedAt(), NEW_DATE);
        key.setCreatedAt(DATE);
    }

    @Test
    public void getKey() throws Exception {
        assertEquals(key.getKey(), KEY);
    }

    @Test
    public void setKey() throws Exception {
        key.setKey(NEW_KEY);
        assertEquals(key.getKey(), NEW_KEY);
        key.setKey(KEY);
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

    @Test
    public void getUpdatedAt() throws Exception {
        assertEquals(key.getUpdatedAt(), DATE);
    }

    @Test
    public void setUpdatedAt() throws Exception {
        key.setUpdatedAt(NEW_DATE);
        assertEquals(key.getUpdatedAt(), NEW_DATE);
        key.setUpdatedAt(DATE);
    }

    @Test
    public void getKeyClass() throws Exception {
        assertEquals(key.getKeyClass(), KEY_CLASS);
    }

    @Test
    public void setKeyClass() throws Exception {
        key.setKeyClass(NEW_KEY_CLASS);
        assertEquals(key.getKeyClass(), NEW_KEY_CLASS);
        key.setKeyClass(NEW_KEY_CLASS);
    }

    @Test
    public void getValidUntil() throws Exception {
        assertEquals(key.getValidUntil(), NEW_DATE);

    }

    @Test
    public void setValidUntil() throws Exception {
        key.setValidUntil(NEW_DATE);
        assertEquals(key.getValidUntil(), NEW_DATE);
        key.setValidUntil(DATE);
    }

}