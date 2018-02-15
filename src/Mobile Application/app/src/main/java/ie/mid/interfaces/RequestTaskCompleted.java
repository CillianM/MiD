package ie.mid.interfaces;

import ie.mid.model.ViewableRequest;
import ie.mid.pojo.IdentityType;

/**
 * Created by Cillian on 13/02/2018.
 */

public interface RequestTaskCompleted {
    void onTaskComplete(IdentityType identityType, ViewableRequest request);
}
