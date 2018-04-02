package ie.mid.identityengine.controller;

import com.google.gson.JsonObject;
import ie.mid.identityengine.dto.InformationRequestDTO;
import ie.mid.identityengine.dto.RequestDTO;
import ie.mid.identityengine.enums.NotificationType;
import ie.mid.identityengine.enums.RequestStatus;
import ie.mid.identityengine.exception.BadRequestException;
import ie.mid.identityengine.model.Party;
import ie.mid.identityengine.model.Request;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.PartyRepository;
import ie.mid.identityengine.repository.RequestRepository;
import ie.mid.identityengine.repository.UserRepository;
import ie.mid.identityengine.service.PushNotificationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestControllerTest {

    @InjectMocks
    private RequestController requestController;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private PushNotificationService pushNotificationService;

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String FCM = "fcm";
    private static final String FIELDS = "KEY,EXPIRY";
    private InformationRequestDTO requestDTO = new InformationRequestDTO();
    private Authentication authentication;


    @Before
    public void setUp() throws Exception {

        User user = new User();
        user.setId(ID);
        user.setNickname(NAME);
        user.setFcmToken(FCM);
        Party party = new Party();
        party.setId(ID);
        party.setName(NAME);
        Request request = new Request();
        request.setId(ID);
        request.setRecipientId(ID);
        request.setSenderId(ID);
        request.setIdentityTypeFields(FIELDS);
        request.setCreatedAt(new Date());
        request.setUpdatedAt(new Date());
        List<Request> requests = new ArrayList<>();
        requests.add(request);
        requestDTO.setRecipientId(ID);
        requestDTO.setSenderId(ID);
        requestDTO.setIndentityTypeId(ID);
        requestDTO.setIdentityTypeFields(FIELDS);
        requestDTO.setIdentityTypeValues(FIELDS);
        requestDTO.setStatus(RequestStatus.ACCEPTED.toString());
        when(requestRepository.save(any(Request.class))).thenReturn(request);
        when(requestRepository.findById(anyString())).thenReturn(request);
        when(requestRepository.findByRecipientId(anyString())).thenReturn(requests);
        when(requestRepository.findBySenderId(anyString())).thenReturn(requests);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(anyString())).thenReturn(user);
        when(partyRepository.save(any(Party.class))).thenReturn(party);
        when(partyRepository.findById(anyString())).thenReturn(party);
        when(pushNotificationService.sendNotifictaionAndData(anyString(), any(JsonObject.class), any(JsonObject.class))).thenReturn(ID);
        when(pushNotificationService.createMessageObject(anyString(), anyString())).thenReturn(new JsonObject());
        when(pushNotificationService.createDataObject(any(NotificationType.class), any(), any())).thenReturn(new JsonObject());
        authentication = new UsernamePasswordAuthenticationToken(ID, ID);
    }

    @Test
    public void getSenderRequests() throws Exception {
        List<RequestDTO> requestDTOs = requestController.getSenderRequests(ID, authentication);
        assertEquals(ID, requestDTOs.get(0).getId());
    }

    @Test
    public void getRecipientRequests() throws Exception {
        List<RequestDTO> requestDTOs = requestController.getRecipientRequests(ID, authentication);
        assertEquals(ID, requestDTOs.get(0).getId());
    }

    @Test
    public void getRequest() throws Exception {
        RequestDTO requestDTO = requestController.getRequest(ID);
        assertEquals(ID, requestDTO.getId());
    }

    @Test
    public void createRequestBadNotification() throws Exception {
        when(pushNotificationService.sendNotifictaionAndData(anyString(), any(JsonObject.class), any(JsonObject.class))).thenThrow(IOException.class);
        RequestDTO requestDTO = requestController.createRequest(this.requestDTO);
        assertEquals(ID, requestDTO.getId());
    }

    @Test(expected = BadRequestException.class)
    public void createRequestBadFields() throws Exception {
        this.requestDTO.setIndentityTypeId(null);
        requestController.createRequest(this.requestDTO);
    }

    @Test(expected = BadRequestException.class)
    public void createRequestBadTypes() throws Exception {
        this.requestDTO.setIdentityTypeFields(ID);
        requestController.createRequest(this.requestDTO);
    }

    @Test
    public void createRequestFromUser() throws Exception {
        RequestDTO requestDTO = requestController.createRequest(this.requestDTO);
        assertEquals(ID, requestDTO.getId());
    }

    @Test
    public void createRequestFromParty() throws Exception {
        RequestDTO requestDTO = requestController.createRequest(this.requestDTO);
        assertEquals(ID, requestDTO.getId());
    }

    @Test
    public void updateRequestBadNotification() throws Exception {
        when(pushNotificationService.sendNotifictaionAndData(anyString(), any(JsonObject.class), any(JsonObject.class))).thenThrow(IOException.class);
        this.requestDTO.setIdentityTypeValues(RequestStatus.ACCEPTED.toString());
        RequestDTO requestDTO = requestController.updateRequest(ID, this.requestDTO);
        assertEquals(ID, requestDTO.getId());
    }

    @Test
    public void updateAcceptedRequest() throws Exception {
        this.requestDTO.setIdentityTypeValues(RequestStatus.ACCEPTED.toString());
        RequestDTO requestDTO = requestController.updateRequest(ID, this.requestDTO);
        assertEquals(ID, requestDTO.getId());
    }

    @Test
    public void updateRejectedRequest() throws Exception {
        this.requestDTO.setIdentityTypeValues(RequestStatus.REJECTED.toString());
        RequestDTO requestDTO = requestController.updateRequest(ID, this.requestDTO);
        assertEquals(ID, requestDTO.getId());
    }

    @Test
    public void updateResindedRequest() throws Exception {
        this.requestDTO.setIdentityTypeValues(RequestStatus.RESCINDED.toString());
        RequestDTO requestDTO = requestController.updateRequest(ID, this.requestDTO);
        assertEquals(ID, requestDTO.getId());
    }

    @Test(expected = BadRequestException.class)
    public void updateBadRequest() {
        this.requestDTO.setRecipientId(null);
        this.requestDTO.setIdentityTypeValues(RequestStatus.DROPPED.toString());
        requestController.updateRequest(ID, this.requestDTO);
        this.requestDTO.setRecipientId(ID);
    }

    @Test
    public void rescindRequest() throws Exception {
        RequestDTO requestDTO = requestController.rescindRequest(ID, authentication);
        requestDTO.setStatus(RequestStatus.RESCINDED.toString());
        assertEquals(ID, requestDTO.getId());
    }

}