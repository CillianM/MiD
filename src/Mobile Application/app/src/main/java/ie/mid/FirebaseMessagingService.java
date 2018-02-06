package ie.mid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import ie.mid.enums.CardStatus;
import ie.mid.handler.DatabaseHandler;
import ie.mid.model.CardType;
import ie.mid.model.CreatedSubmission;

import static android.content.ContentValues.TAG;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public FirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }

    public void sendNotification(String notificationMessage){
        String title = "Submission Request Result";
        String body = "";
        String cardName = "";
        CardType cardType = new CardType();
        try {
            JSONObject submissionObject = new JSONObject(notificationMessage);
            JSONObject dataObject = submissionObject.getJSONObject("data");
            String submissionId = dataObject.getString("submissionId");
            String status = dataObject.getString("status");
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
            handler.open();
            CreatedSubmission submission = handler.getSubmission(submissionId);
            if(submission != null){
                handler.updateCardStatus(submission.getCardId(), status);
                cardType = handler.getUserCard(submission.getCardId());
                cardName = cardType.getTitle();
            }
            else{
                cardName = "your card";
            }
            handler.close();
            switch (status){
                case "ACCEPTED":
                    title = "Successful Submission";
                    body = "Your submission for "+cardName+" has been successfully validated";
                    break;
                case "REJECTED":
                    title = "Unuccessful Submission";
                    body = "Your submission for "+cardName+" has been rejected";
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        if(cardType.getId() != null)
            intent.putExtra("user",cardType.getOwnerId());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        .setContentTitle(title)
                        .setContentText(body);


        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(001, mBuilder.build());
    }
}
