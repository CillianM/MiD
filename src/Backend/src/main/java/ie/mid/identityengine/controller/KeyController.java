package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.KeyDTO;
import ie.mid.identityengine.enums.EntityStatus;
import ie.mid.identityengine.enums.KeyStatus;
import ie.mid.identityengine.model.Key;
import ie.mid.identityengine.repository.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/key")
public class KeyController {

    @Autowired
    KeyRepository keyRepository;

    @Value("${mid.public}")
    private String publicServerKey;

    @Value("${mid.private}")
    private String privateServerKey;

    @GetMapping(value = "/{ownerId}")
    @ResponseBody
    public KeyDTO getKey(@PathVariable String ownerId) {
        if (ownerId.equals("SERVER")) {
            return new KeyDTO("SERVER", "SERVER", publicServerKey, KeyStatus.ACTIVE.toString());
        }
        Key key = keyRepository.findByUserIdAndStatus(ownerId, EntityStatus.ACTIVE.toString());
        return new KeyDTO(key.getId(), key.getUserId(), key.getKey(), key.getStatus());
    }

    @PostMapping()
    @ResponseBody
    public KeyDTO createKey(@RequestBody KeyDTO keyToCreate) {
        Key key = new Key();
        key.setStatus(KeyStatus.ACTIVE.toString());
        key.setKey(keyToCreate.getPublicKey());
        key.setUserId(keyToCreate.getUserId());
        key = keyRepository.save(key);
        return new KeyDTO(key.getId(), key.getUserId(), key.getKey(), key.getStatus());
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public KeyDTO updateKey(@PathVariable String id, @RequestBody KeyDTO keyDTO) {
        Key key = keyRepository.findById(id);
        Key upgradedKey = new Key(keyDTO.getUserId(), keyDTO.getPublicKey());
        key.setStatus(KeyStatus.UPGRADED.toString());
        keyRepository.save(key);
        upgradedKey = keyRepository.save(upgradedKey);
        return new KeyDTO(upgradedKey.getId(), upgradedKey.getUserId(), upgradedKey.getKey(), upgradedKey.getStatus());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public KeyDTO deleteKey(@PathVariable String id) {
        Key key = keyRepository.findById(id);
        key.setStatus(KeyStatus.DELETED.toString());
        return new KeyDTO(key.getId(), key.getUserId(), key.getKey(), key.getStatus());
    }
}
