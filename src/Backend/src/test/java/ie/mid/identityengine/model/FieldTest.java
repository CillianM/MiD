package ie.mid.identityengine.model;

import ie.mid.identityengine.category.UnitTests;
import ie.mid.identityengine.enums.FieldType;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@Category(UnitTests.class)
@RunWith(MockitoJUnitRunner.class)
public class FieldTest {

    private static final String NAME = "name";
    private static final String NEW_NAME = "new_name";
    private Field field = new Field();

    @Before
    public void setUp() {
        field = new Field(NAME, FieldType.ADDRESS);
    }

    @Test
    public void getName() {
        assertEquals(NAME, field.getName());
    }

    @Test
    public void setName() {
        field.setName(NEW_NAME);
        assertEquals(NEW_NAME, field.getName());
        field.setName(NAME);
    }

    @Test
    public void getType() {
    }

    @Test
    public void setType() {
        field.setType(FieldType.BIRTHDAY);
        assertEquals(FieldType.BIRTHDAY.toString(), field.getType());
        field.setName(FieldType.ADDRESS.toString());
    }
}
