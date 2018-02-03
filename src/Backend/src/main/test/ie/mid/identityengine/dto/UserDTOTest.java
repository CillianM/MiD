package ie.mid.identityengine.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserDTOTest {
    @InjectMocks
    private UserDTO user;

    private static final String ID = "id";
    private static final String NEW_ID = "new_id";
    private static final String FCM = "fcm";
    private static final String NEW_FCM = "new_fcm";
    private static final String KEY = "key";
    private static final String NEW_KEY = "new_key";
    private static final String STATUS = "status";
    private static final String NEW_STATUS = "new_status";

    @Before
    public void setUp() throws Exception {
        user = new UserDTO();
        user.setId(ID);
        user.setFcmToken(FCM);
        user.setKeyId(ID);
        user.setPublicKey(KEY);
        user.setStatus(STATUS);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(ID, user.getId());
    }

    @Test
    public void setId() throws Exception {
        user.setId(NEW_ID);
        assertEquals(NEW_ID, user.getId());
        user.setId(ID);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(STATUS, user.getStatus());
    }

    @Test
    public void setStatus() throws Exception {
        user.setStatus(NEW_STATUS);
        assertEquals(NEW_STATUS, user.getStatus());
        user.setStatus(STATUS);
    }

    @Test
    public void getFcmToken() throws Exception {
        assertEquals(FCM, user.getFcmToken());
    }

    @Test
    public void setFcmToken() throws Exception {
        user.setFcmToken(NEW_FCM);
        assertEquals(NEW_FCM, user.getFcmToken());
        user.setFcmToken(FCM);
    }

    @Test
    public void getPublicKey() throws Exception {
        assertEquals(KEY, user.getPublicKey());
    }

    @Test
    public void setPublicKey() throws Exception {
        user.setPublicKey(NEW_KEY);
        assertEquals(NEW_KEY, user.getPublicKey());
        user.setPublicKey(KEY);
    }

    @Test
    public void getKeyId() throws Exception {
        assertEquals(ID, user.getKeyId());
    }

    @Test
    public void setKeyId() throws Exception {
        user.setKeyId(NEW_ID);
        assertEquals(NEW_ID, user.getKeyId());
        user.setKeyId(ID);
    }


}