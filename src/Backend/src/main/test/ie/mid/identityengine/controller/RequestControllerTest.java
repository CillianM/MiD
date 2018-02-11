package ie.mid.identityengine.controller;

import com.google.gson.JsonObject;
import ie.mid.identityengine.dto.InformationRequestDTO;
import ie.mid.identityengine.dto.RequestDTO;
import ie.mid.identityengine.enums.NotificationType;
import ie.mid.identityengine.model.Request;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.RequestRepository;
import ie.mid.identityengine.repository.UserRepository;
import ie.mid.identityengine.service.PushNotificationService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    private PushNotificationService pushNotificationService;

    private static final String ID = "id";
    private static final String FCM = "fcm";
    private static final String FIELDS = "one,two";
    private InformationRequestDTO requestDTO = new InformationRequestDTO();


    @Before
    public void setUp() throws Exception {

        User user = new User();
        user.setId(ID);
        user.setFcmToken(FCM);
        Request request = new Request();
        request.setId(ID);
        request.setIdentityTypeFields(FIELDS);
        requestDTO.setRecipientId(ID);
        requestDTO.setSenderId(ID);
        requestDTO.setIndentityTypeId(ID);
        requestDTO.setIdentityTypeFields(FIELDS);
        when(requestRepository.save(any(Request.class))).thenReturn(request);
        when(requestRepository.findById(anyString())).thenReturn(request);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(anyString())).thenReturn(user);
        when(pushNotificationService.createMessageObject(anyString(), anyString())).thenReturn(new JsonObject());
        when(pushNotificationService.createDataObject(any(NotificationType.class), any(), any())).thenReturn(new JsonObject());

    }

    @Test
    public void getRequest() throws Exception {
        RequestDTO requestDTO = requestController.getRequest(ID);
        assertEquals(ID, requestDTO.getId());
    }

    @Test
    public void createRequest() throws Exception {
        RequestDTO requestDTO = requestController.createRequest(this.requestDTO);
        assertEquals(ID, requestDTO.getId());
    }

    @Test
    public void updateRequest() throws Exception {
        RequestDTO requestDTO = requestController.updateRequest(ID, this.requestDTO);
        assertEquals(ID, requestDTO.getId());
    }

    @Test
    public void rescindRequest() throws Exception {
        RequestDTO requestDTO = requestController.rescindRequest(ID);
        assertEquals(ID, requestDTO.getId());
    }

}