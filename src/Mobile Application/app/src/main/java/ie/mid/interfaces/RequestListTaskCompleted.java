package ie.mid.interfaces;

import java.util.List;

import ie.mid.model.ViewableRequest;
import ie.mid.pojo.Request;
import ie.mid.pojo.Submission;

public interface RequestListTaskCompleted {
    void onTaskComplete(List<ViewableRequest> requestList);
}
