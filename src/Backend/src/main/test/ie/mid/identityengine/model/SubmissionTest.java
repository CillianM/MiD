package ie.mid.identityengine.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

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
        assertEquals(submission.getId(), ID);
    }

    @Test
    public void setId() throws Exception {
        submission.setId(NEW_ID);
        assertEquals(submission.getId(), NEW_ID);
        submission.setId(ID);
    }

    @Test
    public void getUserId() throws Exception {
        assertEquals(submission.getUserId(), ID);
    }

    @Test
    public void setUserId() throws Exception {
        submission.setUserId(NEW_ID);
        assertEquals(submission.getUserId(), NEW_ID);
        submission.setUserId(ID);
    }

    @Test
    public void getPartyId() throws Exception {
        assertEquals(submission.getPartyId(), ID);
    }

    @Test
    public void setPartyId() throws Exception {
        submission.setPartyId(NEW_ID);
        assertEquals(submission.getPartyId(), NEW_ID);
        submission.setPartyId(ID);
    }

    @Test
    public void getData() throws Exception {
        assertEquals(submission.getData(), DATA);
    }

    @Test
    public void setData() throws Exception {
        submission.setData(NEW_DATA);
        assertEquals(submission.getData(), NEW_DATA);
        submission.setData(DATA);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(submission.getStatus(), STATUS);
    }

    @Test
    public void setStatus() throws Exception {
        submission.setStatus(NEW_STATUS);
        assertEquals(submission.getStatus(), NEW_STATUS);
        submission.setStatus(STATUS);
    }

    @Test
    public void getCreatedAt() throws Exception {
        assertEquals(submission.getCreatedAt(), DATE);
    }

    @Test
    public void setCreatedAt() throws Exception {
        submission.setCreatedAt(NEW_DATE);
        assertEquals(submission.getCreatedAt(), NEW_DATE);
        submission.setCreatedAt(DATE);
    }

    @Test
    public void getUpdatedAt() throws Exception {
        assertEquals(submission.getUpdatedAt(), DATE);
    }

    @Test
    public void setUpdatedAt() throws Exception {
        submission.setUpdatedAt(NEW_DATE);
        assertEquals(submission.getUpdatedAt(), NEW_DATE);
        submission.setUpdatedAt(DATE);
    }
}