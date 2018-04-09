package ie.mid.identityengine.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IndividualTest {

    private Individual individual;
    private static final String CLASS = "CLASS";
    private static final String ID = "ID";

    @Before
    public void setUp() throws Exception {
        individual = new Individual();
        individual.setIndividualId(ID);
        individual.set$class(CLASS);
    }

    @Test
    public void get$class() {
        assertEquals(CLASS, individual.get$class());
    }

    @Test
    public void getIndividualId() {
        assertEquals(ID, individual.getIndividualId());

    }
}