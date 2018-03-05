package ie.mid.interfaces;

import ie.mid.pojo.Certificate;

/**
 * Created by Cillian on 26/02/2018.
 */

public interface CertificateTaskCompleted {
    void onTaskComplete(Certificate certificate);
}
