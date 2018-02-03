package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.KeyDTO;
import ie.mid.identityengine.dto.UserDTO;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.UserRepository;
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
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserRepository userRepository;

    @Mock
    KeyController keyController;

    private static final String ID = "id";
    private static final String KEY = "key";
    private static final String FCM = "fcm";
    private UserDTO userDTO = new UserDTO();

    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setId(ID);
        userDTO.setPublicKey(KEY);
        userDTO.setFcmToken(FCM);
        KeyDTO key = new KeyDTO();
        key.setId(ID);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(anyString())).thenReturn(user);
        when(keyController.createKey(any(KeyDTO.class))).thenReturn(key);
        when(keyController.getKey(anyString())).thenReturn(key);
    }

    @Test
    public void getUser() throws Exception {
        UserDTO userDTO = userController.getUser(ID);
        assertEquals(ID, userDTO.getId());
    }

    @Test
    public void createUser() throws Exception {
        UserDTO userDTO = userController.createUser(this.userDTO);
        assertEquals(ID, userDTO.getId());
    }

    @Test
    public void updateUserToken() throws Exception {
        UserDTO userDTO = userController.updateUserToken(ID, ID);
        assertEquals(ID, userDTO.getId());
    }

    @Test
    public void deleteUser() throws Exception {
        UserDTO userDTO = userController.deleteUser(ID);
        assertEquals(ID, userDTO.getId());
    }

}