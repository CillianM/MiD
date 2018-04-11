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
    public void getCreatedAt() throws Exception {
        assertEquals(DATE, user.getCreatedAt());
    }

    @Test
    public void setCreatedAt() throws Exception {
        user.setCreatedAt(NEW_DATE);
        assertEquals(NEW_DATE, user.getCreatedAt());
        user.setCreatedAt(DATE);
    }

    @Test
    public void getUpdatedAt() throws Exception {
        assertEquals(DATE, user.getUpdatedAt());
    }

    @Test
    public void setUpdatedAt() throws Exception {
        user.setUpdatedAt(NEW_DATE);
        assertEquals(NEW_DATE, user.getUpdatedAt());
        user.setUpdatedAt(DATE);
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

}