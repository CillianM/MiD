package ie.mid.identityengine.security;

import ie.mid.identityengine.enums.AuthorityType;
import ie.mid.identityengine.enums.EntityStatus;
import ie.mid.identityengine.model.Authority;
import ie.mid.identityengine.model.Key;
import ie.mid.identityengine.model.Party;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.KeyRepository;
import ie.mid.identityengine.repository.PartyRepository;
import ie.mid.identityengine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private KeyRepository keyRepository;

    @Override
    public Authentication authenticate(Authentication authentication) {

        String id = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<Authority> authority = new ArrayList<>();

        User user = userRepository.findById(id);
        Party party = partyRepository.findById(id);
        if (user != null || party != null){
            Key key = keyRepository.findByUserIdAndStatus(id, EntityStatus.ACTIVE.toString());
            if(key != null && id.equals(DataEncryption.decryptText(password, key.getPublicKey()))){
                if(user != null) authority.add(new Authority(AuthorityType.USER.toString()));
                else authority.add(new Authority(AuthorityType.PARTY.toString()));
            }
            return new UsernamePasswordAuthenticationToken(id, password, authority);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
