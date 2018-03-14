package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ie.mid.backend.SubmissionService;
import ie.mid.interfaces.SubmissionListTaskCompleted;
import ie.mid.model.CardType;
import ie.mid.model.HttpCall;
import ie.mid.model.Profile;
import ie.mid.pojo.Submission;
import ie.mid.util.EncryptionUtil;
import ie.mid.util.InternetUtil;

public class SubmissionListGetter extends AsyncTask<Void, Void, List<Submission>> {

    private List<CardType> cardTypes;
    private SubmissionListTaskCompleted callBack;
    private SubmissionService submissionService;
    private WeakReference<Context> context;
    private Profile profile;

    public SubmissionListGetter(Context context, SubmissionListTaskCompleted callBack, Profile profile,List<CardType> cardTypes){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.submissionService = new SubmissionService(context);
        this.cardTypes = cardTypes;
        this.profile = profile;
    }

    @Override
    protected List<Submission> doInBackground(Void... voids) {
        if(InternetUtil.isServerLive(context.get())) {
            HttpCall httpCall = new HttpCall();
            String id = profile.getServerId();
            String password = EncryptionUtil.encryptText(profile.getServerToken(),profile.getPrivateKey());
            if(password != null) {
                httpCall.setAuthHeader(id,password);
                List<Submission> submissionList = new ArrayList<>();
                for(CardType cardType: cardTypes) {
                    String submissionId = cardType.getSubmissionId();
                    if(submissionId != null) {
                        Submission submission = submissionService.getSubmission(submissionId,httpCall);
                        if(submission != null) submissionList.add(submission);
                    }
                }
                return submissionList;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Submission> result) {
        callBack.onTaskComplete(result);
    }

    @Override
    protected void onPreExecute() {
    }

}
