package ie.mid.identityengine.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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