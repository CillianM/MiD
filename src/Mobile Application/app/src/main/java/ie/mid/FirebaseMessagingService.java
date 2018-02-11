package ie.mid;

import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String LOG_TAG="FIREBASE_SERVICE";
    public FirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.i(LOG_TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.i(LOG_TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i(LOG_TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            processNotification(remoteMessage.getNotification().getBody(),remoteMessage.getData());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See processNotification method below.

    }

    public void processNotification(String body, Map<String, String> data){
            Log.i(LOG_TAG,"Notification Body: " + body);
            Log.i(LOG_TAG,"Notification Data: " + data.toString());
    }
}
