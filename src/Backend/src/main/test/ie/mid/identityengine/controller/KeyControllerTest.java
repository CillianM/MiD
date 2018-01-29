package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.KeyDTO;
import ie.mid.identityengine.model.Key;
import ie.mid.identityengine.repository.KeyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KeyControllerTest {

    @InjectMocks
    private KeyController keyController;

    @Mock
    private KeyRepository keyRepository;

    private static final String ID = "id";

    @Before
    public void setUp() throws Exception {
        Key key = new Key();
        key.setId(ID);
        when(keyRepository.findById(anyString())).thenReturn(key);
        when(keyRepository.findByUserIdAndStatus(anyString(), anyString())).thenReturn(key);
        when(keyRepository.save(any(Key.class))).thenReturn(key);
    }

    @Test
    public void getKey() throws Exception {
        KeyDTO key = keyController.getKey(ID);
        assertEquals(key.getId(), ID);
    }

    @Test
    public void createKey() throws Exception {
        KeyDTO key = keyController.createKey(new KeyDTO());
        assertEquals(key.getId(), ID);
    }

    @Test
    public void updateKey() throws Exception {
        KeyDTO key = keyController.updateKey(ID, new KeyDTO());
        assertEquals(key.getId(), ID);
    }

    @Test
    public void deleteKey() throws Exception {
        KeyDTO key = keyController.deleteKey(ID);
        assertEquals(key.getId(), ID);
    }

}