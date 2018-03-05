package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import ie.mid.backend.SubmissionService;
import ie.mid.interfaces.SubmitTaskCompleted;
import ie.mid.model.HttpCall;
import ie.mid.model.Profile;
import ie.mid.pojo.Submission;
import ie.mid.util.EncryptionUtil;
import ie.mid.util.InternetUtil;

/**
 * Created by Cillian on 13/02/2018.
 */

public class SubmissionCreator extends AsyncTask<Submission, Void, Submission> {

    private WeakReference<Context> context;
    private SubmitTaskCompleted callBack;
    private SubmissionService submissionService;
    private Profile profile;

    public SubmissionCreator(Context context,SubmitTaskCompleted callBack, SubmissionService submissionService,Profile profile){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.submissionService = submissionService;
        this.profile = profile;
    }

    @Override
    protected Submission doInBackground(Submission... submissions) {
        if(InternetUtil.isServerLive(context.get())) {
            HttpCall httpCall = new HttpCall();
            httpCall.setJsonBody(submissions[0].toJsonString());
            String id = profile.getServerId();
            String password = EncryptionUtil.encryptText(id,profile.getPrivateKey());
            if(password != null) {
                httpCall.setAuthHeader(id,password);
                return submissionService.submitIdentity(httpCall);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Submission result) {
        callBack.onTaskComplete(result);
    }

    @Override
    protected void onPreExecute() {
    }

}
