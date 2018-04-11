package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import ie.mid.backend.SubmissionService;
import ie.mid.interfaces.SubmissionTaskCompleted;
import ie.mid.model.HttpCall;
import ie.mid.model.Profile;
import ie.mid.pojo.Submission;
import ie.mid.util.EncryptionUtil;
import ie.mid.util.InternetUtil;

public class SubmissionGetter extends AsyncTask<Void, Void, Submission> {

    private String submissionId;
    private SubmissionTaskCompleted callBack;
    private SubmissionService submissionService;
    private WeakReference<Context> context;
    private Profile profile;

    public SubmissionGetter(Context context, SubmissionTaskCompleted callBack, Profile profile, String submissionId){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.submissionService = new SubmissionService(context);
        this.submissionId = submissionId;
        this.profile = profile;
    }

    @Override
    protected Submission doInBackground(Void... voids) {
        if(InternetUtil.isServerLive(context.get())) {
            HttpCall httpCall = new HttpCall();
            String id = profile.getServerId();
            String password = EncryptionUtil.encryptText(profile.getServerToken(),profile.getPrivateKey());
            if(password != null) {
                httpCall.setAuthHeader(id,password);
                return submissionService.getSubmission(submissionId,httpCall);
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