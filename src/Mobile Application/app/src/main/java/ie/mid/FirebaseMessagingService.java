package ie.mid;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ie.mid.handler.DatabaseHandler;
import ie.mid.model.Profile;

import static android.content.ContentValues.TAG;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String LOG_TAG="FIREBASE_SERVICE";
    String NOTIFICATION_CHANNEL_ID = "MID";
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
        if (remoteMessage.getNotification() != null && remoteMessage.getData().size() > 0 ) {
            Log.i(LOG_TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            processNotification(remoteMessage.getNotification().getBody(),remoteMessage.getData());
        }

    }

    public void processNotification(String body, Map<String, String> data){
        Log.i(LOG_TAG,"Notification Body: " + body);
        Log.i(LOG_TAG,"Notification Data: " + data.toString());

        String title = "MiD";
        boolean isSubmission;
        String submissionId = null;
        String requestId = null;
        if(data.get("type").equals("REQUEST")){
            title += " Information Request";
            isSubmission = false;
            requestId = data.get("requestId");
        } else{
            title += " Submission Update";
            isSubmission = true;
            submissionId = data.get("submissionId");
        }
        String serverId =  data.get("serverId");
        int requestID = (int) System.currentTimeMillis();
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        Intent notificationIntent = new Intent(getApplicationContext(), LoginActivity.class);;
        if(isSubmission){
            notificationIntent.putExtra("submissionId",submissionId);
        }else{
            notificationIntent.putExtra("requestId",requestId);
        }
        notificationIntent.putExtra("serverId",serverId);
        notificationIntent.putExtra("type",data.get("type"));
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "mid_notify");
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(body);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("mid_notify",
                    "MiD Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }
}
