package ie.mid.identityengine.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    @InjectMocks
    private User user;

    private static final String ID = "id";
    private static final String NEW_ID = "new_id";
    private static final String FCM = "fcm";
    private static final String NEW_FCM = "new_fcm";
    private static final String STATUS = "status";
    private static final String NEW_STATUS = "new_status";
    private static final Date DATE = new Date();
    private static final Date NEW_DATE = new Date();

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(ID);
        user.setFcmToken(FCM);
        user.setStatus(STATUS);
        user.setCreatedAt(DATE);
        user.setUpdatedAt(DATE);
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
    public void getCreatedAt() throws Exception {
        assertEquals(user.getCreatedAt(), DATE);
    }

    @Test
    public void setCreatedAt() throws Exception {
        user.setCreatedAt(NEW_DATE);
        assertEquals(user.getCreatedAt(), NEW_DATE);
        user.setCreatedAt(DATE);
    }

    @Test
    public void getUpdatedAt() throws Exception {
        assertEquals(user.getUpdatedAt(), DATE);
    }

    @Test
    public void setUpdatedAt() throws Exception {
        user.setUpdatedAt(NEW_DATE);
        assertEquals(user.getUpdatedAt(), NEW_DATE);
        user.setUpdatedAt(DATE);
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

}