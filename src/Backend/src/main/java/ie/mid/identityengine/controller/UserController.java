package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.KeyDTO;
import ie.mid.identityengine.dto.UserDTO;
import ie.mid.identityengine.enums.EntityStatus;
import ie.mid.identityengine.exception.BadRequestException;
import ie.mid.identityengine.exception.HyperledgerErrorException;
import ie.mid.identityengine.exception.ResourceForbiddenException;
import ie.mid.identityengine.exception.ResourceNotFoundException;
import ie.mid.identityengine.model.Individual;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.UserRepository;
import ie.mid.identityengine.service.HyperledgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    KeyController keyController;

    @Autowired
    HyperledgerService hyperledgerService;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public UserDTO getUser(@PathVariable String id) {
        User user = userRepository.findById(id);
        if (user == null) throw new ResourceNotFoundException("No user exists");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setNickname(user.getNickname());
        userDTO.setFcmToken(user.getFcmToken());
        userDTO.setStatus(user.getStatus());
        KeyDTO latestKey = keyController.getKey(id);
        if (latestKey == null) throw new ResourceNotFoundException("No key exists for user");
        userDTO.setKeyId(latestKey.getId());
        userDTO.setPublicKey(latestKey.getPublicKey());
        return userDTO;
    }

    @PostMapping()
    @ResponseBody
    public UserDTO createUser(@RequestBody UserDTO userToCreate) {
        if (isInvalidUser(userToCreate)) throw new BadRequestException();
        //Create Party in blockchain
        Individual individual = hyperledgerService.createIndividual();
        if(individual == null) throw new HyperledgerErrorException("Error creating party");

        User user = new User();
        user.setNickname(userToCreate.getNickname());
        user.setFcmToken(userToCreate.getFcmToken());
        user.setNetworkId(individual.getIndividualId());
        user.setStatus(EntityStatus.ACTIVE.toString());
        user = userRepository.save(user);
        userToCreate.setId(user.getId());
        //create key for user
        KeyDTO keyDTO = new KeyDTO();
        keyDTO.setPublicKey(userToCreate.getPublicKey());
        keyDTO.setUserId(user.getId());
        keyDTO = keyController.createKey(keyDTO);
        userToCreate.setKeyId(keyDTO.getId());
        userToCreate.setStatus(user.getStatus());
        return userToCreate;
    }

    @PutMapping(value = "/{id}/token")
    @ResponseBody
    public UserDTO updateUserToken(@PathVariable String id, @RequestBody String token, Authentication authentication) {
        User user = userRepository.findById(id);
        if (user == null) throw new ResourceNotFoundException();
        if (!user.getId().equals(authentication.getName()))
            throw new ResourceForbiddenException(authentication.getName() + " is forbidden from resource " + id);
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
    public UserDTO deleteUser(@PathVariable String id, Authentication authentication) {
        User user = userRepository.findById(id);
        if (user == null) throw new ResourceNotFoundException();
        if (!user.getId().equals(authentication.getName()))
            throw new ResourceForbiddenException(authentication.getName() + " is forbidden from resource " + id);
        user.setStatus(EntityStatus.DELETED.toString());
        userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setStatus(user.getStatus());
        userDTO.setFcmToken(user.getFcmToken());
        userDTO.setId(user.getId());
        return userDTO;
    }

    private boolean isInvalidUser(UserDTO userDTO) {
        return userDTO.getNickname() == null || userDTO.getFcmToken() == null || userDTO.getPublicKey() == null;
    }
}
