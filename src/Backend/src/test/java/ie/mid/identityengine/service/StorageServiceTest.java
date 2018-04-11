package ie.mid.identityengine.service;

import ie.mid.identityengine.category.UnitTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

@Category(UnitTests.class)
@RunWith(MockitoJUnitRunner.class)
public class StorageServiceTest {

    @Autowired
    StorageService storageService;

    private static final String NAME = "name";
    private static final String DATA = "data";
    private static final String PATH = "/uploads/name";


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void saveData() {
    }

    @Test
    public void loadData() {
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void init() {
    }
}