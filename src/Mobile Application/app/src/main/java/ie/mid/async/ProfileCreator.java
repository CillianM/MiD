package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import ie.mid.backend.UserService;
import ie.mid.interfaces.ProfileTaskCompleted;
import ie.mid.model.Profile;
import ie.mid.util.InternetUtil;

/**
 * Created by Cillian on 13/02/2018.
 */

public class ProfileCreator extends AsyncTask<Profile, Void, Profile> {

    private ProfileTaskCompleted callBack;
    private UserService userService;
    private WeakReference<Context> context;

    public ProfileCreator(Context context, ProfileTaskCompleted callBack, UserService userService){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.userService = userService;
    }

    @Override
    protected Profile doInBackground(Profile... profiles) {
        if(InternetUtil.isServerLive(context.get()))
            return userService.createUser(profiles[0]);
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
