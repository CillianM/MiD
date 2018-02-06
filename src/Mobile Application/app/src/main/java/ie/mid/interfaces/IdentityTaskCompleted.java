package ie.mid.interfaces;

import java.util.List;

import ie.mid.model.AvailableCard;
import ie.mid.pojo.IdentityType;

public interface IdentityTaskCompleted {
    void onTaskComplete(List<IdentityType> identityTypes);
}
