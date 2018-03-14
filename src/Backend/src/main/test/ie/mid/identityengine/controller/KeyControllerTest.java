package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.KeyDTO;
import ie.mid.identityengine.dto.TokenDTO;
import ie.mid.identityengine.model.Key;
import ie.mid.identityengine.repository.KeyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

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
    private static final String KEY = "key";
    private static final String SERVER = "SERVER";
    private KeyDTO keyDTO = new KeyDTO();
    private TokenDTO tokenDTO = new TokenDTO();
    private Authentication authentication;

    @Before
    public void setUp() throws Exception {
        Key key = new Key();
        key.setId(ID);
        key.setPublicKey(KEY);
        key.setUserId(ID);
        keyDTO.setId(ID);
        keyDTO.setPublicKey(KEY);
        keyDTO.setUserId(ID);
        tokenDTO.setId(ID);
        tokenDTO.setToken(ID);
        tokenDTO.setUserId(ID);
        when(keyRepository.findById(anyString())).thenReturn(key);
        when(keyRepository.findByUserIdAndStatus(anyString(), anyString())).thenReturn(key);
        when(keyRepository.save(any(Key.class))).thenReturn(key);

        authentication = new UsernamePasswordAuthenticationToken(ID, ID);
    }

    @Test
    public void getKey() throws Exception {
        KeyDTO key = keyController.getKey(ID);
        assertEquals(ID, key.getId());
    }

    @Test
    public void getKeyForServer() throws Exception {
        KeyDTO key = keyController.getKey(SERVER);
        assertEquals(SERVER, key.getId());
    }

    @Test
    public void createKey() throws Exception {
        KeyDTO key = keyController.createKey(this.keyDTO);
        assertEquals(ID, key.getId());
    }

    @Test
    public void updateKey() throws Exception {
        KeyDTO key = keyController.updateKey(ID, this.keyDTO);
        assertEquals(ID, key.getId());
    }

    @Test
    public void updateToken() throws Exception {
        TokenDTO tokenDTO = keyController.updateToken(ID, this.tokenDTO);
        assertEquals(ID, tokenDTO.getId());
    }

    @Test
    public void deleteKey() throws Exception {
        KeyDTO key = keyController.deleteKey(ID, authentication);
        assertEquals(ID, key.getId());
    }

}