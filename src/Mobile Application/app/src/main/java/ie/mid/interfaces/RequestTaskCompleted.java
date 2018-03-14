package ie.mid.interfaces;

import ie.mid.pojo.IdentityType;
import ie.mid.pojo.Request;

/**
 * Created by Cillian on 13/02/2018.
 */

public interface RequestTaskCompleted {
    void onTaskComplete(IdentityType identityType, Request request);
}
