package ie.mid.interfaces;

import java.util.List;

import ie.mid.pojo.Request;
import ie.mid.pojo.Submission;

public interface RequestTaskCompleted {
    void onTaskComplete(List<Submission> submissionList, List<Request> requestList);
}
