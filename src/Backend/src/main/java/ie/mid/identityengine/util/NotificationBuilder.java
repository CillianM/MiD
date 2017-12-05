package ie.mid.identityengine.util;

import org.json.JSONObject;

public class NotificationBuilder {

    public static JSONObject createNotification(String heading, String body,String to, String [] data){
        JSONObject bodyObject = new JSONObject();
        bodyObject.put("token", to);
        bodyObject.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", heading);
        notification.put("body", body);

        JSONObject dataObject = new JSONObject();
        for(int i = 0; i < data.length; i++){
            dataObject.put("Key-" + i, data[i]);
        }

        bodyObject.put("notification", notification);
        bodyObject.put("data", data);

        return notification;
    }
}
