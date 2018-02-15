package ie.mid.async;

import android.os.AsyncTask;

import ie.mid.backend.SubmissionService;
import ie.mid.interfaces.SubmitTaskCompleted;
import ie.mid.pojo.Submission;

/**
 * Created by Cillian on 13/02/2018.
 */

public class SubmissionCreator extends AsyncTask<Submission, Void, Submission> {

    private SubmitTaskCompleted callBack;
    private SubmissionService submissionService;

    public SubmissionCreator(SubmitTaskCompleted callBack, SubmissionService submissionService){
        this.callBack = callBack;
        this.submissionService = submissionService;
    }

    @Override
    protected Submission doInBackground(Submission... submissions) {
        return submissionService.submitIdentity(submissions[0]);
    }

    @Override
    protected void onPostExecute(Submission result) {
        callBack.onTaskComplete(result);
    }

    @Override
    protected void onPreExecute() {
    }

}
