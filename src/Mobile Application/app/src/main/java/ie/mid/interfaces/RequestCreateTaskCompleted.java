package ie.mid.interfaces;

import ie.mid.pojo.Request;

/**
 * Created by Cillian on 13/02/2018.
 */

public interface RequestCreateTaskCompleted {
    void onTaskComplete(Request request);
}
