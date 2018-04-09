package ie.mid.identityengine.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CertificateUpdateTest {

    private CertificateUpdate certificateUpdate;
    private static final String CLASS = "CLASS";
    private static final String CERT = "CERT";
    private static final String STATUS = "STATUS";

    @Before
    public void setUp() throws Exception {
        certificateUpdate = new CertificateUpdate();
        certificateUpdate.set$class(CLASS);
        certificateUpdate.setCertificate(CERT);
        certificateUpdate.setNewStatus(STATUS);
    }

    @Test
    public void get$class() {
        assertEquals(CLASS, certificateUpdate.get$class());
    }

    @Test
    public void getCertificate() {
        assertEquals(CERT, certificateUpdate.getCertificate());
    }

    @Test
    public void getNewStatus() {
        assertEquals(STATUS, certificateUpdate.getNewStatus());
    }
}