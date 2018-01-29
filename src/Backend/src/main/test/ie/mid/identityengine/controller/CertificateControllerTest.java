package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.CertificateDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class CertificateControllerTest {

    @InjectMocks
    private CertificateController certificateController;

    private static final String ID = "id";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getCertificate() throws Exception {
        CertificateDTO certificateDTO = certificateController.getCertificate(ID);
        assertNotNull(certificateDTO);
    }

}