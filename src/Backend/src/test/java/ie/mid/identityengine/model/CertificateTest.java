package ie.mid.identityengine.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CertificateTest {

    private Certificate certificate;
    private static final String CLASS = "CLASS";
    private static final String ID = "ID";
    private static final String STATUS = "STATUS";
    private static final String DATE = "DATE";


    @Before
    public void setUp() throws Exception {
        certificate = new Certificate();
        certificate.setOwner(ID);
        certificate.setTrustee(ID);
        certificate.setCertId(ID);
        certificate.set$class(CLASS);
        certificate.setDateCreated(DATE);
        certificate.setStatus(STATUS);
    }

    @Test
    public void get$class() {
        assertEquals(CLASS, certificate.get$class());
    }

    @Test
    public void getCertId() {
        assertEquals(ID, certificate.getCertId());

    }

    @Test
    public void getDateCreated() {
        assertEquals(DATE, certificate.getDateCreated());

    }

    @Test
    public void getTrustee() {
        assertEquals(ID, certificate.getTrustee());

    }

    @Test
    public void getOwner() {
        assertEquals(ID, certificate.getOwner());

    }

    @Test
    public void getStatus() {
        assertEquals(STATUS, certificate.getStatus());

    }
}