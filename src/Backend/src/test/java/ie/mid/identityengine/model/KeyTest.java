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

@Category(UnitTests.class)
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
        key.setPublicKey(KEY);
        key.setStatus(STATUS);
        key.setValidUntil(DATE);
        key.setCreatedAt(DATE);
        key.setUpdatedAt(DATE);
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
        assertEquals(STATUS, key.getStatus());
    }

    @Test
    public void setStatus() throws Exception {
        key.setStatus(NEW_STATUS);
        assertEquals(NEW_STATUS, key.getStatus());
        key.setStatus(STATUS);
    }

    @Test
    public void getCreatedAt() throws Exception {
        assertEquals(DATE, key.getCreatedAt());
    }

    @Test
    public void setCreatedAt() throws Exception {
        key.setCreatedAt(NEW_DATE);
        assertEquals(NEW_DATE, key.getCreatedAt());
        key.setCreatedAt(DATE);
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

    @Test
    public void getUpdatedAt() throws Exception {
        assertEquals(DATE, key.getUpdatedAt());
    }

    @Test
    public void setUpdatedAt() throws Exception {
        key.setUpdatedAt(NEW_DATE);
        assertEquals(NEW_DATE, key.getUpdatedAt());
        key.setUpdatedAt(DATE);
    }

    @Test
    public void getValidUntil() throws Exception {
        assertEquals(NEW_DATE, key.getValidUntil());

    }

    @Test
    public void setValidUntil() throws Exception {
        key.setValidUntil(NEW_DATE);
        assertEquals(NEW_DATE, key.getValidUntil());
        key.setValidUntil(DATE);
    }

}