package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.KeyDTO;
import ie.mid.identityengine.dto.NewKeyDTO;
import ie.mid.identityengine.dto.TokenDTO;
import ie.mid.identityengine.enums.EntityStatus;
import ie.mid.identityengine.enums.KeyStatus;
import ie.mid.identityengine.exception.BadRequestException;
import ie.mid.identityengine.exception.ResourceForbiddenException;
import ie.mid.identityengine.exception.ResourceNotFoundException;
import ie.mid.identityengine.model.Key;
import ie.mid.identityengine.repository.KeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Controller
@RequestMapping(value = "/key")
public class KeyController {

    @Autowired
    KeyRepository keyRepository;

    private Logger logger = LoggerFactory.getLogger(KeyController.class);

    @GetMapping(value = "/{ownerId}")
    @ResponseBody
    public KeyDTO getKey(@PathVariable String ownerId) {
        logger.debug("'GET' request to getKey() for id " + ownerId);
        Key key = keyRepository.findByUserIdAndStatus(ownerId, EntityStatus.ACTIVE.toString());
        if (key == null) {
            logger.error("No key found for owner id " + ownerId);
            throw new ResourceNotFoundException();
        }
        logger.debug("Key found, returning...");
        return new KeyDTO(key.getId(), key.getUserId(), key.getPublicKey(), key.getStatus());
    }

    @PostMapping()
    @ResponseBody
    public NewKeyDTO createKey(@RequestBody KeyDTO keyToCreate) {
        logger.debug("'POST' request to createKey()");
        if (isInvalidKey(keyToCreate)) {
            logger.error("Invalid key: " + keyToCreate.toString());
            throw new BadRequestException();
        }
        Key key = new Key();
        key.setToken(UUID.randomUUID().toString());
        key.setStatus(KeyStatus.ACTIVE.toString());
        key.setPublicKey(keyToCreate.getPublicKey());
        key.setUserId(keyToCreate.getUserId());
        key = keyRepository.save(key);

        NewKeyDTO createdKey = new NewKeyDTO();
        createdKey.setId(key.getId());
        createdKey.setUserId(key.getUserId());
        createdKey.setPublicKey(key.getPublicKey());
        createdKey.setToken(key.getToken());
        logger.debug("Key created, returning...");
        return createdKey;
    }

    @PreAuthorize("#keyDTO.userId == authentication.name")
    @PutMapping(value = "/{id}")
    @ResponseBody
    public KeyDTO updateKey(@PathVariable String id, @RequestBody KeyDTO keyDTO) {
        logger.debug("'PUT' request to updateKey() for id " + id);
        if (isInvalidKey(keyDTO)) {
            logger.error("Invalid key: " + keyDTO.toString());
            throw new BadRequestException();
        }
        Key key = keyRepository.findById(id);
        if (key == null) {
            logger.error("No key found for id " + id);
            throw new ResourceNotFoundException();
        }
        Key upgradedKey = new Key(keyDTO.getUserId(), keyDTO.getPublicKey());
        key.setStatus(KeyStatus.UPGRADED.toString());
        keyRepository.save(key);
        upgradedKey = keyRepository.save(upgradedKey);
        logger.debug("Key updated, returning");
        return new KeyDTO(upgradedKey.getId(), upgradedKey.getUserId(), upgradedKey.getPublicKey(), upgradedKey.getStatus());
    }

    @PreAuthorize("#tokenDTO.userId == authentication.name")
    @PutMapping(value = "/token/{id}")
    @ResponseBody
    public TokenDTO updateToken(@PathVariable String id, @RequestBody TokenDTO tokenDTO) {
        logger.debug("'PUT' request to updateToken() for id " + id);
        if (isInvalidToken(tokenDTO)) {
            logger.error("Invalid token: " + tokenDTO.toString());
            throw new BadRequestException();
        }
        Key key = keyRepository.findById(id);
        if (key == null) {
            logger.error("No key found for id " + id);
            throw new ResourceNotFoundException();
        }
        Key upgradedKey = new Key(key.getUserId(), key.getPublicKey());
        upgradedKey.setToken(UUID.randomUUID().toString());
        key.setStatus(KeyStatus.UPGRADED.toString());
        keyRepository.save(key);
        upgradedKey = keyRepository.save(upgradedKey);
        logger.debug("Token updated, returning...");
        return new TokenDTO(upgradedKey.getId(), upgradedKey.getUserId(), upgradedKey.getToken(), upgradedKey.getStatus());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public KeyDTO deleteKey(@PathVariable String id, Authentication authentication) {
        logger.debug("'DELETE' request to deleteKey() for id " + id);
        Key key = keyRepository.findById(id);
        if (key == null) {
            logger.error("No key found for id " + id);
            throw new ResourceNotFoundException();
        }
        if (!key.getUserId().equals(authentication.getName())) {
            logger.error("Authentication failure: " + authentication.getName() + " != " + key.getUserId());
            throw new ResourceForbiddenException(authentication.getName() + " cannot access resource resource " + id);
        }
        key.setStatus(KeyStatus.DELETED.toString());
        return new KeyDTO(key.getId(), key.getUserId(), key.getPublicKey(), key.getStatus());
    }

    private boolean isInvalidKey(KeyDTO keyDTO) {

        if (keyDTO.getUserId() == null || keyDTO.getPublicKey() == null) {
            logger.error("KeyDTO contains null parameters: " + keyDTO.toString());
            return true;
        }
        if (keyDTO.getUserId().isEmpty() || keyDTO.getPublicKey().isEmpty()) {
            logger.error("KeyDTO contains empty parameters: " + keyDTO.toString());
            return true;
        }
        return false;
    }

    private boolean isInvalidToken(TokenDTO tokenDTO) {
        if (tokenDTO.getUserId() == null || tokenDTO.getToken() == null) {
            logger.error("TokenDTO contains null parameters: " + tokenDTO.toString());
            return true;
        }
        if (tokenDTO.getUserId().isEmpty() || tokenDTO.getToken().isEmpty()) {
            logger.error("TokenDTO contains empty parameters: " + tokenDTO.toString());
            return true;
        }
        return false;
    }

}
