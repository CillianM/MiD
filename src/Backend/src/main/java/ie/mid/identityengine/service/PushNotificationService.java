package ie.mid.identityengine.service;

import ie.mid.identityengine.enums.NotificationType;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class PushNotificationService {

    private static final String FIREBASE_SERVER_KEY = "AAAAf-To7f8:APA91bHrZGYfe9rUg_BOP1qHg-2p7_d-2USBuPuZpybo6Y3nt-XixzNK1DTsNRW1nx95YxTfudyLz0FfL5fbqEP2F3cLBv556rgK0vrRC3-dPdy12fMBWhrG0AEK73cP8tSbAkzDbWi6";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    public String sendNotification(String deviceId, JSONObject notification,JSONObject data){
        try {
            URL url = new URL(FIREBASE_API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key=" + FIREBASE_SERVER_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject message = new JSONObject();
            message.put("to", deviceId.trim());
            message.put("notification", notification);
            message.put("data", data);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(message.toString());
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else return null;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public JSONObject createNotification(String heading, NotificationType type, String[] keys, Object[] data) {

        JSONObject bodyObject = new JSONObject();
        bodyObject.put("type",type.toString());

        JSONObject dataObject = new JSONObject();
        for (int i = 0; i < data.length; i++) {
            dataObject.put(keys[i], data[i]);
        }
        bodyObject.put("data",dataObject);

        JSONObject info = new JSONObject();
        info.put("title", heading); // Notification title
        info.put("body", bodyObject); // Notification body

        return info;
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
