package ie.mid.identityengine.service;

import ie.mid.identityengine.category.UnitTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@Category(UnitTests.class)
@RunWith(MockitoJUnitRunner.class)
public class HyperledgerServiceTest {

    @InjectMocks
    HyperledgerService hyperledgerService;

    @Mock
    HttpService httpService;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getCertificate() {
    }

    @Test
    public void createCertificate() {
    }

    @Test
    public void updateCertificate() {
    }

    @Test
    public void getIdentifyingParty() {
    }

    @Test
    public void createIdentifyingParty() {
    }

    @Test
    public void getIndividual() {
    }

    @Test
    public void createIndividual() {
    }
}