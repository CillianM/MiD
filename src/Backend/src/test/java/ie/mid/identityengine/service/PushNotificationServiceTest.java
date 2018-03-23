package ie.mid.identityengine.service;

import com.google.gson.JsonObject;
import ie.mid.identityengine.enums.NotificationType;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PushNotificationServiceTest {

    @Autowired
    PushNotificationService pushNotificationService;
    @Mock
    private HttpClient defaultHttpClient;

    private static final String REQUEST_HEADER = "header";
    private static final String MESSAGE = "message";
    private static final String DATA = "data";
    private static final String TO = "to";
    private JsonObject MESSAGE_OBJECT = new JsonObject();
    private JsonObject DATA_OBJECT = new JsonObject();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(PushNotificationService.class);
        pushNotificationService = new PushNotificationService();
        this.pushNotificationService.httpClient = defaultHttpClient;
        when(defaultHttpClient.execute(any(HttpPost.class), any(BasicResponseHandler.class))).thenReturn(DATA);
    }

    @Test
    public void createMessageObject() {
        JsonObject messageObject = pushNotificationService.createMessageObject(
                REQUEST_HEADER,
                MESSAGE);
        assertTrue(messageObject.toString().contains(MESSAGE));
    }

    @Test
    public void createDataObject() {
        JsonObject dataObject = pushNotificationService.createDataObject(NotificationType.REQUEST, new String[]{"fields"},
                new String[]{DATA}
        );
        assertTrue(dataObject.toString().contains(DATA));
    }

    @Test
    public void sendTopicData() throws Exception {
        String result = pushNotificationService.sendTopicData(TO, DATA_OBJECT);
        assertEquals(DATA, result);
    }

    @Test
    public void sendTopicNotification() throws Exception {
        String result = pushNotificationService.sendTopicNotification(TO, MESSAGE_OBJECT);
        assertEquals(DATA, result);
    }

    @Test
    public void sendTopicNotificationAndData() throws Exception {
        String result = pushNotificationService.sendTopicNotificationAndData(TO, MESSAGE_OBJECT, DATA_OBJECT);
        assertEquals(DATA, result);
    }

    @Test
    public void sendNotifictaionAndData() throws Exception {
        String result = pushNotificationService.sendNotifictaionAndData(TO, MESSAGE_OBJECT, DATA_OBJECT);
        assertEquals(DATA, result);
    }
}