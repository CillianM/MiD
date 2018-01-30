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
        assertEquals(user.getId(), ID);
    }

    @Test
    public void setId() throws Exception {
        user.setId(NEW_ID);
        assertEquals(user.getId(), NEW_ID);
        user.setId(ID);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(user.getStatus(), STATUS);
    }

    @Test
    public void setStatus() throws Exception {
        user.setStatus(NEW_STATUS);
        assertEquals(user.getStatus(), NEW_STATUS);
        user.setStatus(STATUS);
    }

    @Test
    public void getFcmToken() throws Exception {
        assertEquals(user.getFcmToken(), FCM);
    }

    @Test
    public void setFcmToken() throws Exception {
        user.setFcmToken(NEW_FCM);
        assertEquals(user.getFcmToken(), NEW_FCM);
        user.setFcmToken(FCM);
    }

    @Test
    public void getPublicKey() throws Exception {
        assertEquals(user.getPublicKey(), KEY);
    }

    @Test
    public void setPublicKey() throws Exception {
        user.setPublicKey(NEW_KEY);
        assertEquals(user.getPublicKey(), NEW_KEY);
        user.setPublicKey(KEY);
    }

    @Test
    public void getKeyId() throws Exception {
        assertEquals(user.getKeyId(), ID);
    }

    @Test
    public void setKeyId() throws Exception {
        user.setKeyId(NEW_ID);
        assertEquals(user.getKeyId(), NEW_ID);
        user.setKeyId(ID);
    }


}