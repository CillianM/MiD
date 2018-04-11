package ie.mid.identityengine.model;

import ie.mid.identityengine.category.UnitTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@Category(UnitTests.class)
@RunWith(MockitoJUnitRunner.class)
public class IdentifyingPartyTest {

    private IdentifyingParty identifyingParty;
    private static final String CLASS = "CLASS";
    private static final String ID = "ID";

    @Before
    public void setUp() throws Exception {
        identifyingParty = new IdentifyingParty();
        identifyingParty.setPartyId(ID);
        identifyingParty.set$class(CLASS);
    }

    @Test
    public void get$class() {
        assertEquals(CLASS, identifyingParty.get$class());
    }

    @Test
    public void getPartyId() {
        assertEquals(ID, identifyingParty.getPartyId());

    }
}