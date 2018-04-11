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
public class AuthorityTest {

    private Authority authority;
    private static final String USER = "USER";

    @Before
    public void setUp() throws Exception {
        authority = new Authority(USER);
    }

    @Test
    public void getAuthority() {
        assertEquals(USER, authority.getAuthority());
    }

}