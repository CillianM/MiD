package ie.mid.identityengine.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SubmissionDTOTest {
    @InjectMocks
    private SubmissionDTO submission;

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
        submission = new SubmissionDTO();
        submission.setId(ID);
        submission.setUserId(ID);
        submission.setPartyId(ID);
        submission.setData(DATA);
        submission.setStatus(STATUS);
        submission.setDate(DATE.toString());
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
    public void getDate() throws Exception {
        assertEquals(submission.getDate(), DATE.toString());
    }

    @Test
    public void setDate() throws Exception {
        submission.setDate(NEW_DATE.toString());
        assertEquals(submission.getDate(), NEW_DATE.toString());
        submission.setDate(DATE.toString());
    }
}