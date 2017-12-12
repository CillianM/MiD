package ie.mid.identityengine.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class PushNotificationService {

    private static final String FIREBASE_SERVER_KEY = "AAAAf-To7f8:APA91bHrZGYfe9rUg_BOP1qHg-2p7_d-2USBuPuZpybo6Y3nt-XixzNK1DTsNRW1nx95YxTfudyLz0FfL5fbqEP2F3cLBv556rgK0vrRC3-dPdy12fMBWhrG0AEK73cP8tSbAkzDbWi6";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    public String sendNotification(JSONObject message){
        HttpEntity<String> request = new HttpEntity<>(message.toString());

        CompletableFuture<String> pushNotification = sendMessage(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            return pushNotification.get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        return null;
    }

    public JSONObject createNotification(String heading, String body, String to, String[] keys, Object[] data) {
        JSONObject bodyObject = new JSONObject();
        bodyObject.put("token", to);
        bodyObject.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", heading);
        notification.put("body", body);

        JSONObject dataObject = new JSONObject();
        for (int i = 0; i < data.length; i++) {
            dataObject.put(keys[i], data[i]);
        }

        bodyObject.put("notification", notification);
        bodyObject.put("data", data);

        return notification;
    }

    @Async
    CompletableFuture<String> sendMessage(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();
        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptorService("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptorService("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);


    }
}
