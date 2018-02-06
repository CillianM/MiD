package ie.mid.interfaces;

import ie.mid.pojo.Submission;

/**
 * Created by Cillian on 06/02/2018.
 */

public interface SubmitTaskCompleted {
    void onTaskComplete(Submission submission);
}
