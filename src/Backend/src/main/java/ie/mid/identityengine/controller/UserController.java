package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.UserDTO;
import ie.mid.identityengine.enums.EntityStatus;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public UserDTO getUser(@PathVariable String id) {
        User user = userRepository.findById(id);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFcmToken(user.getFcmToken());
        userDTO.setStatus(user.getStatus());
        return userDTO;
    }

    @PostMapping()
    @ResponseBody
    public UserDTO createUser(@RequestBody UserDTO userToCreate) {
        User user = new User();
        user.setFcmToken(userToCreate.getFcmToken());
        user.setStatus(EntityStatus.ACTIVE.toString());
        userToCreate.setId(userRepository.save(user).getId());
        return userToCreate;
    }

    @PutMapping(value = "/{id}/token")
    @ResponseBody
    public UserDTO updateUserToken(@PathVariable String id, @RequestBody String token) {
        User user = userRepository.findById(id);
        user.setFcmToken(token);
        userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setStatus(user.getStatus());
        userDTO.setFcmToken(user.getFcmToken());
        userDTO.setId(user.getId());
        return userDTO;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public UserDTO deleteUser(@PathVariable String id) {
        User user = userRepository.findById(id);
        user.setStatus(EntityStatus.DELETED.toString());
        userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setStatus(user.getStatus());
        userDTO.setFcmToken(user.getFcmToken());
        userDTO.setId(user.getId());
        return userDTO;
    }
}
