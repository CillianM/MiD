package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ie.mid.backend.SubmissionService;
import ie.mid.interfaces.SubmissionListTaskCompleted;
import ie.mid.model.CardType;
import ie.mid.pojo.Submission;
import ie.mid.util.InternetUtil;

public class SubmissionListGetter extends AsyncTask<Void, Void, List<Submission>> {

    private List<CardType> cardTypes;
    private SubmissionListTaskCompleted callBack;
    private SubmissionService submissionService;
    private WeakReference<Context> context;

    public SubmissionListGetter(Context context, SubmissionListTaskCompleted callBack, List<CardType> cardTypes){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.submissionService = new SubmissionService(context);
        this.cardTypes = cardTypes;
    }

    @Override
    protected List<Submission> doInBackground(Void... voids) {
        if(InternetUtil.isServerLive(context.get())) {
            List<Submission> submissionList = new ArrayList<>();
            for(CardType cardType: cardTypes) {
                String submissionId = cardType.getSubmissionId();
                if(submissionId != null) {
                    Submission submission = submissionService.getSubmission(submissionId);
                    if(submission != null) submissionList.add(submission);
                }
            }
            return submissionList;
        }
        else
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
