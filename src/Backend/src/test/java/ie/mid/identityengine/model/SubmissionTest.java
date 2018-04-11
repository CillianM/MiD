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
public class SubmissionTest {

    @InjectMocks
    private Submission submission;

    private static final String ID = "id";
    private static final String NEW_ID = "new_id";
    private static final String DATA = "data";
    private static final String NEW_DATA = "new_data";
    private static final String STATUS = "status";
    private static final String NEW_STATUS = "new_status";
    private static final Date DATE = new Date();
    private static final Date NEW_DATE = new Date();

    @Before
    public void setUp() throws Exception {
        submission = new Submission();
        submission.setId(ID);
        submission.setUserId(ID);
        submission.setPartyId(ID);
        submission.setData(DATA);
        submission.setStatus(STATUS);
        submission.setCreatedAt(DATE);
        submission.setUpdatedAt(DATE);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(ID, submission.getId());
    }

    @Test
    public void setId() throws Exception {
        submission.setId(NEW_ID);
        assertEquals(NEW_ID, submission.getId());
        submission.setId(ID);
    }

    @Test
    public void getUserId() throws Exception {
        assertEquals(ID, submission.getUserId());
    }

    @Test
    public void setUserId() throws Exception {
        submission.setUserId(NEW_ID);
        assertEquals(NEW_ID, submission.getUserId());
        submission.setUserId(ID);
    }

    @Test
    public void getPartyId() throws Exception {
        assertEquals(ID, submission.getPartyId());
    }

    @Test
    public void setPartyId() throws Exception {
        submission.setPartyId(NEW_ID);
        assertEquals(NEW_ID, submission.getPartyId());
        submission.setPartyId(ID);
    }

    @Test
    public void getData() throws Exception {
        assertEquals(DATA, submission.getData());
    }

    @Test
    public void setData() throws Exception {
        submission.setData(NEW_DATA);
        assertEquals(NEW_DATA, submission.getData());
        submission.setData(DATA);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(STATUS, submission.getStatus());
    }

    @Test
    public void setStatus() throws Exception {
        submission.setStatus(NEW_STATUS);
        assertEquals(NEW_STATUS, submission.getStatus());
        submission.setStatus(STATUS);
    }

    @Test
    public void getCreatedAt() throws Exception {
        assertEquals(DATE, submission.getCreatedAt());
    }

    @Test
    public void setCreatedAt() throws Exception {
        submission.setCreatedAt(NEW_DATE);
        assertEquals(NEW_DATE, submission.getCreatedAt());
        submission.setCreatedAt(DATE);
    }

    @Test
    public void getUpdatedAt() throws Exception {
        assertEquals(DATE, submission.getUpdatedAt());
    }

    @Test
    public void setUpdatedAt() throws Exception {
        submission.setUpdatedAt(NEW_DATE);
        assertEquals(NEW_DATE, submission.getUpdatedAt());
        submission.setUpdatedAt(DATE);
    }
}