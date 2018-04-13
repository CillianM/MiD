package ie.mid.identityengine.service;

import com.google.gson.JsonObject;
import ie.mid.identityengine.enums.NotificationType;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PushNotificationService {
    @Value("${mid.fcm}")
    private String fcmServerKey;
    private static final String FIREBASE_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String TOPIC_URL = "/topics/";
    HttpClient httpClient;
    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    public PushNotificationService() {
        this.httpClient = HttpClientBuilder.create().build();
    }

    public JsonObject createMessageObject(String title, String body, String clickAction) {
        JsonObject notificationObject = new JsonObject();
        notificationObject.addProperty("title",title);
        notificationObject.addProperty("body",body);
        notificationObject.addProperty("sound", "default");
        notificationObject.addProperty("click_action", clickAction);
        return notificationObject;
    }

    public JsonObject createDataObject(NotificationType type, String[] keys, String[] data){
        JsonObject dataObject = new JsonObject();
        for (int i = 0; i < data.length; i++) {
            dataObject.addProperty(keys[i], data[i]);
        }
        dataObject.addProperty("type",type.toString());
        return dataObject;
    }

    public String sendTopicData(String topic, JsonObject dataObject) throws IOException{
        return sendNotifictaionAndData(TOPIC_URL + topic, null, dataObject);
    }

    public String sendTopicNotification(String topic, JsonObject notificationObject) throws IOException{
        return sendNotifictaionAndData(TOPIC_URL + topic, notificationObject, null);
    }

    public String sendTopicNotificationAndData(String topic, JsonObject notificationObject, JsonObject dataObject) throws IOException{
        return sendNotifictaionAndData(TOPIC_URL + topic, notificationObject, dataObject);
    }

    public String sendNotifictaionAndData(String to, JsonObject notificationObject, JsonObject dataObject) throws IOException {
        String result = null;
        JsonObject sendObject = new JsonObject();
        sendObject.addProperty("to", to);
        result = sendNotification(sendObject, notificationObject, dataObject);
        return result;
    }

    private String sendNotification(JsonObject sendObject, JsonObject notificationObject, JsonObject dataObject) throws IOException {
        logger.debug("Sending notification to " + FIREBASE_URL);
        HttpPost httpPost = new HttpPost(FIREBASE_URL);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "key=" + fcmServerKey);

        if (notificationObject != null) {
            logger.debug("Adding notification" + notificationObject.toString());
            sendObject.add("notification", notificationObject);
        }
        if (dataObject != null) {
            logger.debug("Adding data: " + dataObject.toString());
            sendObject.add("data", dataObject);
        }

        String data = sendObject.toString();
        StringEntity entity = new StringEntity(data);
        httpPost.setEntity(entity);

        BasicResponseHandler responseHandler = new BasicResponseHandler();
        String response = httpClient.execute(httpPost, responseHandler);
        logger.debug("Firebase Reponse: " + response);
        return response;
    }

}