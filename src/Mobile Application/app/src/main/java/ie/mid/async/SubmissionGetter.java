package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ie.mid.backend.PartyService;
import ie.mid.backend.SubmissionService;
import ie.mid.interfaces.SubmissionListTaskCompleted;
import ie.mid.interfaces.SubmissionTaskCompleted;
import ie.mid.model.CardType;
import ie.mid.model.ViewableSubmission;
import ie.mid.pojo.Party;
import ie.mid.pojo.Submission;
import ie.mid.util.InternetUtil;

/**
 * Created by Cillian on 13/02/2018.
 */

public class SubmissionGetter extends AsyncTask<Void, Void, Submission> {

    private String submissionId;
    private SubmissionTaskCompleted callBack;
    private SubmissionService submissionService;
    private WeakReference<Context> context;

    public SubmissionGetter(Context context, SubmissionTaskCompleted callBack, String submissionId){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.submissionService = new SubmissionService(context);
        this.submissionId = submissionId;
    }

    @Override
    protected Submission doInBackground(Void... voids) {
        if(InternetUtil.isServerLive(context.get())) {
            Submission submission = submissionService.getSubmission(submissionId);
            if(submission != null) {
                return submission;
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