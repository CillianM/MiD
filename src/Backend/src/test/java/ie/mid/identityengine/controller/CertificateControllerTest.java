package ie.mid.identityengine.controller;

import ie.mid.identityengine.category.UnitTests;
import ie.mid.identityengine.dto.CertificateDTO;
import ie.mid.identityengine.model.Certificate;
import ie.mid.identityengine.model.Party;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.PartyRepository;
import ie.mid.identityengine.repository.UserRepository;
import ie.mid.identityengine.service.HyperledgerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@Category(UnitTests.class)
@RunWith(MockitoJUnitRunner.class)
public class CertificateControllerTest {

    @InjectMocks
    private CertificateController certificateController;

    @Mock
    private HyperledgerService hyperledgerService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PartyRepository partyRepository;

    private static final String ID = "id";
    private Authentication authentication;

    @Before
    public void setUp() throws Exception {

        Certificate certificate = new Certificate();
        certificate.setCertId(ID);
        certificate.setOwner(ID);
        certificate.setTrustee(ID);
        User user = new User();
        user.setId(ID);
        user.setNickname(ID);
        Party party = new Party();
        party.setId(ID);
        party.setName(ID);
        when(hyperledgerService.createCertificate(anyString(), anyString())).thenReturn(certificate);
        when(hyperledgerService.getCertificate(anyString())).thenReturn(certificate);
        when(userRepository.findById(anyString())).thenReturn(user);
        when(partyRepository.findById(anyString())).thenReturn(party);
        when(userRepository.findByNetworkId(anyString())).thenReturn(user);
        when(partyRepository.findByNetworkId(anyString())).thenReturn(party);

        authentication = new UsernamePasswordAuthenticationToken(ID, ID);
    }

    @Test
    public void getCertificate() throws Exception {
        CertificateDTO certificateDTO = certificateController.getCertificate(ID);
        assertNotNull(certificateDTO);
    }

    @Test
    public void deleteCertificate() throws Exception {
        CertificateDTO certificateDTO = certificateController.deleteCertificate(ID, authentication);
        assertNotNull(certificateDTO);
    }

}