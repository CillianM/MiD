package ie.mid.identityengine.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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