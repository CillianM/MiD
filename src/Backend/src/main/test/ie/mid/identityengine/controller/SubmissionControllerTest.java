package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.SubmissionDTO;
import ie.mid.identityengine.model.Submission;
import ie.mid.identityengine.repository.SubmissionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubmissionControllerTest {

    @InjectMocks
    private SubmissionController submissionController;

    @Mock
    private SubmissionRepository submissionRepository;

    private static final String ID = "id";


    @Before
    public void setUp() throws Exception {
        Submission submission = new Submission();
        submission.setId(ID);
        submission.setCreatedAt(new Date());
        submission.setUpdatedAt(new Date());
        List<Submission> submissionList = new ArrayList<>();
        submissionList.add(submission);
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);
        when(submissionRepository.findById(anyString())).thenReturn(submission);
        when(submissionRepository.findByPartyId(anyString())).thenReturn(submissionList);
        when(submissionRepository.findByUserId(anyString())).thenReturn(submissionList);
    }

    @Test
    public void getPartySubmissions() throws Exception {
        List<SubmissionDTO> submissionList = submissionController.getPartySubmissions(ID);
        assertFalse(submissionList.isEmpty());
    }

    @Test
    public void getUserSubmissions() throws Exception {
        List<SubmissionDTO> submissionList = submissionController.getUserSubmissions(ID);
        assertFalse(submissionList.isEmpty());
    }

    @Test
    public void getSubmission() throws Exception {
        SubmissionDTO submissionDTO = submissionController.getSubmission(ID);
        assertEquals(submissionDTO.getId(), ID);
    }

    @Test
    public void createSubmission() throws Exception {
        SubmissionDTO submissionDTO = submissionController.createSubmission(new SubmissionDTO());
        assertEquals(submissionDTO.getId(), ID);
    }

    @Test
    public void updateSubmission() throws Exception {
        SubmissionDTO submissionDTO = submissionController.updateSubmission(ID, new SubmissionDTO());
        assertNotNull(submissionDTO);
    }

}