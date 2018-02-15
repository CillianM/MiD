package ie.mid.interfaces;

import java.util.List;

import ie.mid.model.ViewableSubmission;
import ie.mid.pojo.Request;
import ie.mid.pojo.Submission;

public interface SubmissionTaskCompleted {
    void onTaskComplete(ViewableSubmission submission);
}
