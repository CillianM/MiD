package ie.mid.identityengine.controller;

import com.google.gson.JsonObject;
import ie.mid.identityengine.dto.SubmissionDTO;
import ie.mid.identityengine.enums.NotificationType;
import ie.mid.identityengine.model.Submission;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.SubmissionRepository;
import ie.mid.identityengine.repository.UserRepository;
import ie.mid.identityengine.service.PushNotificationService;
import ie.mid.identityengine.service.StorageService;
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

    @Mock
    private PushNotificationService pushNotificationService;

    @Mock
    private StorageService storageService;

    @Mock
    private UserRepository userRepository;

    private static final String ID = "id";
    private static final String DATA = "data";
    private static final String ACCEPTED = "ACCEPTED";
    private static final String FCM = "fcm";
    private static final String PATH = "path";

    private SubmissionDTO submissionDTO = new SubmissionDTO();


    @Before
    public void setUp() throws Exception {
        Submission submission = new Submission();
        submission.setId(ID);
        submission.setData(PATH);
        submission.setCreatedAt(new Date());
        submission.setUpdatedAt(new Date());
        submissionDTO.setId(ID);
        submissionDTO.setData(DATA);
        submissionDTO.setPartyId(ID);
        submissionDTO.setUserId(ID);
        submissionDTO.setStatus(ACCEPTED);
        List<Submission> submissionList = new ArrayList<>();
        submissionList.add(submission);
        User user = new User();
        user.setId(ID);
        user.setFcmToken(FCM);
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);
        when(submissionRepository.findById(anyString())).thenReturn(submission);
        when(submissionRepository.findByPartyId(anyString())).thenReturn(submissionList);
        when(submissionRepository.findByUserId(anyString())).thenReturn(submissionList);
        when(pushNotificationService.sendNotifictaionAndData(anyString(), any(JsonObject.class), any(JsonObject.class))).thenReturn(DATA);
        when(pushNotificationService.createMessageObject(anyString(), anyString())).thenReturn(new JsonObject());
        when(pushNotificationService.createDataObject(any(NotificationType.class), any(), any())).thenReturn(new JsonObject());
        when(userRepository.findById(anyString())).thenReturn(user);
        when(storageService.saveData(anyString())).thenReturn(PATH);
        when(storageService.loadData(PATH)).thenReturn(DATA);
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
        assertEquals(ID, submissionDTO.getId());
    }

    @Test
    public void createSubmission() throws Exception {
        SubmissionDTO submissionDTO = submissionController.createSubmission(this.submissionDTO);
        assertEquals(ID, submissionDTO.getId());
    }

    @Test
    public void updateSubmission() throws Exception {
        SubmissionDTO submissionDTO = submissionController.updateSubmission(ID, this.submissionDTO);
        assertNotNull(submissionDTO);
    }

}