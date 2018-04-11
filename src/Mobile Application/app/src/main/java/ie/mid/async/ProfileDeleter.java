package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import ie.mid.backend.UserService;
import ie.mid.interfaces.ProfileTaskCompleted;
import ie.mid.model.HttpCall;
import ie.mid.model.Profile;
import ie.mid.pojo.Request;
import ie.mid.pojo.User;
import ie.mid.util.EncryptionUtil;
import ie.mid.util.InternetUtil;

/**
 * Created by Cillian on 11/04/2018.
 */

public class ProfileDeleter extends AsyncTask<Profile, Void, Profile> {

    private ProfileTaskCompleted callBack;
    private UserService userService;

    private WeakReference<Context> context;

    public ProfileDeleter(Context context, ProfileTaskCompleted callBack){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.userService = new UserService(context);
    }

    @Override
    protected Profile doInBackground(Profile... profiles) {
        Profile profile = profiles[0];
        if(InternetUtil.isServerLive(context.get())) {
            HttpCall httpCall = new HttpCall();
            String id = profile.getServerId();
            String password = EncryptionUtil.encryptText(profile.getServerToken(), profile.getPrivateKey());
            if(password != null) {
                httpCall.setAuthHeader(id, password);
                User user = userService.deleteUser(profile.getServerId(),httpCall);
                if(user != null){
                    return profile;
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Profile result) {
        callBack.onTaskComplete(result);
    }

    @Override
    protected void onPreExecute() {
    }
}
