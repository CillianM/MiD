package ie.mid;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ie.mid.backend.UserService;

import static android.content.ContentValues.TAG;

/**
 * Created by Cillian on 15/11/2017.
 */

public class FirebaseTokenController extends FirebaseInstanceIdService {

    UserService userService = new UserService(BaseApplication.getAppContext());

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        userService.updateFcm(refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }
}
