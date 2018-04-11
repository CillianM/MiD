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