package ie.mid.interfaces;

import java.util.List;

import ie.mid.pojo.Request;
import ie.mid.pojo.Submission;

public interface SubmissionListTaskCompleted {
    void onTaskComplete(List<Submission> submissionList);
}
