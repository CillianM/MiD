package ie.mid.identityengine.controller;

import com.google.gson.JsonObject;
import ie.mid.identityengine.category.UnitTests;
import ie.mid.identityengine.dto.SubmissionDTO;
import ie.mid.identityengine.enums.NotificationType;
import ie.mid.identityengine.model.Certificate;
import ie.mid.identityengine.model.Party;
import ie.mid.identityengine.model.Submission;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.PartyRepository;
import ie.mid.identityengine.repository.SubmissionRepository;
import ie.mid.identityengine.repository.UserRepository;
import ie.mid.identityengine.service.HyperledgerService;
import ie.mid.identityengine.service.PushNotificationService;
import ie.mid.identityengine.service.StorageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@Category(UnitTests.class)
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

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private HyperledgerService hyperledgerService;

    private static final String ID = "id";
    private static final String DATA = "data";
    private static final String ACCEPTED = "ACCEPTED";
    private static final String FCM = "fcm";
    private static final String PATH = "path";

    private SubmissionDTO submissionDTO = new SubmissionDTO();
    private Authentication authentication;


    @Before
    public void setUp() throws Exception {
        Submission submission = new Submission();
        submission.setId(ID);
        submission.setUserId(ID);
        submission.setPartyId(ID);
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
        user.setNickname(ID);
        user.setFcmToken(FCM);
        Party party = new Party();
        party.setId(ID);
        party.setName(ID);
        Certificate certificate = new Certificate();
        certificate.setCertId(ID);
        certificate.setOwner(ID);
        certificate.setTrustee(ID);
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);
        when(submissionRepository.findById(anyString())).thenReturn(submission);
        when(submissionRepository.findByPartyId(anyString())).thenReturn(submissionList);
        when(submissionRepository.findByUserId(anyString())).thenReturn(submissionList);
        when(pushNotificationService.sendNotifictaionAndData(anyString(), any(JsonObject.class), any(JsonObject.class))).thenReturn(DATA);
        when(pushNotificationService.createMessageObject(anyString(), anyString(), anyString())).thenReturn(new JsonObject());
        when(pushNotificationService.createDataObject(any(NotificationType.class), any(), any())).thenReturn(new JsonObject());
        when(userRepository.findById(anyString())).thenReturn(user);
        when(partyRepository.findById(anyString())).thenReturn(party);
        when(storageService.saveData(anyString())).thenReturn(PATH);
        when(storageService.loadData(PATH)).thenReturn(DATA);
        when(hyperledgerService.createCertificate(anyString(), anyString(), anyString())).thenReturn(certificate);

        authentication = new UsernamePasswordAuthenticationToken(ID, ID);
    }

    @Test
    public void getPartySubmissions() throws Exception {
        List<SubmissionDTO> submissionList = submissionController.getPartySubmissions(ID, authentication);
        assertFalse(submissionList.isEmpty());
    }

    @Test
    public void getUserSubmissions() throws Exception {
        List<SubmissionDTO> submissionList = submissionController.getUserSubmissions(ID, authentication);
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
        SubmissionDTO submissionDTO = submissionController.updateSubmission(ID, this.submissionDTO, authentication);
        assertNotNull(submissionDTO);
    }

}