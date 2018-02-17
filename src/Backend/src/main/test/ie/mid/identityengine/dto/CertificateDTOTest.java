package ie.mid.identityengine.dto;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;

public class CertificateDTOTest {

    @InjectMocks
    private CertificateDTO certificate;
    private static final String ID = "id";
    private static final String OWNED_BY = "owned_by";
    private static final String CREATED_BY = "created_by";
    private static final String CREATED_AT = "created_at";
    private static final String NEW_ID = "new_id";
    private static final String NEW_OWNED_BY = "new_owned_by";
    private static final String NEW_CREATED_BY = "new_created_by";
    private static final String NEW_CREATED_AT = "new_created_at";

    @Before
    public void setUp() throws Exception {
        certificate = new CertificateDTO();
        certificate.setId(ID);
        certificate.setOwnedBy(OWNED_BY);
        certificate.setCreatedBy(CREATED_BY);
        certificate.setCreatedAt(CREATED_AT);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(ID, certificate.getId());
    }

    @Test
    public void setId() throws Exception {
        certificate.setId(NEW_ID);
        assertEquals(NEW_ID, certificate.getId());
        certificate.setId(ID);
    }

    @Test
    public void getOwnedBy() throws Exception {
        assertEquals(OWNED_BY, certificate.getOwnedBy());
    }

    @Test
    public void setOwnedBy() throws Exception {
        certificate.setOwnedBy(NEW_OWNED_BY);
        assertEquals(NEW_OWNED_BY, certificate.getOwnedBy());
        certificate.setOwnedBy(OWNED_BY);
    }

    @Test
    public void getCreatedBy() throws Exception {
        assertEquals(CREATED_BY, certificate.getCreatedBy());
    }

    @Test
    public void setCreatedBy() throws Exception {
        certificate.setCreatedBy(NEW_CREATED_BY);
        assertEquals(NEW_CREATED_BY, certificate.getCreatedBy());
        certificate.setCreatedBy(CREATED_BY);
    }

    @Test
    public void getCreatedAt() throws Exception {
        assertEquals(CREATED_AT, certificate.getCreatedAt());
    }

    @Test
    public void setCreatedAt() throws Exception {
        certificate.setCreatedAt(NEW_CREATED_AT);
        assertEquals(NEW_CREATED_AT, certificate.getCreatedAt());
        certificate.setCreatedAt(CREATED_AT);
    }

}