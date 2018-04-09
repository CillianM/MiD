package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.KeyDTO;
import ie.mid.identityengine.dto.NewKeyDTO;
import ie.mid.identityengine.dto.PartyDTO;
import ie.mid.identityengine.model.IdentifyingParty;
import ie.mid.identityengine.model.Party;
import ie.mid.identityengine.repository.PartyRepository;
import ie.mid.identityengine.security.KeyUtil;
import ie.mid.identityengine.service.HyperledgerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PartyControllerTest {

    @InjectMocks
    private PartyController partyController;

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private KeyController keyController;

    @Mock
    private HyperledgerService hyperledgerService;

    private static final String ID = "id";
    private static final String NAME = "name";
    private PartyDTO partyDTO = new PartyDTO();
    private Authentication authentication;



    @Before
    public void setUp() throws Exception {
        KeyPair keyPair = KeyUtil.generateKeyPair();
        partyDTO.setPublicKey(KeyUtil.byteToBase64(keyPair.getPublic().getEncoded()).replace("\n", ""));
        partyDTO.setId(ID);
        partyDTO.setName(NAME);
        Party party = new Party();
        party.setId(ID);
        party.setName(NAME);
        List<Party> parties = new ArrayList<>();
        parties.add(party);
        NewKeyDTO key = new NewKeyDTO();
        key.setId(ID);
        key.setPublicKey(KeyUtil.byteToBase64(keyPair.getPublic().getEncoded()).replace("\n", ""));
        IdentifyingParty identifyingParty = new IdentifyingParty();
        identifyingParty.setPartyId(ID);
        when(partyRepository.save(any(Party.class))).thenReturn(party);
        when(partyRepository.findById(anyString())).thenReturn(party);
        when(partyRepository.findAll()).thenReturn(parties);
        when(keyController.createKey(any(KeyDTO.class))).thenReturn(key);
        when(keyController.getKey(anyString())).thenReturn(key);
        when(hyperledgerService.createIdentifyingParty()).thenReturn(identifyingParty);

        authentication = new UsernamePasswordAuthenticationToken(ID, ID);
    }

    @Test
    public void getParties() throws Exception {
        List<PartyDTO> parties = partyController.getParties();
        assertFalse(parties.isEmpty());
    }

    @Test
    public void getParty() throws Exception {
        PartyDTO partyDTO = partyController.getParty(ID);
        assertEquals(ID, partyDTO.getId());
    }

    @Test
    public void createParty() throws Exception {
        PartyDTO partyDTO = partyController.createParty(this.partyDTO);
        assertEquals(ID, partyDTO.getId());
    }

    @Test
    public void updateParty() throws Exception {
        PartyDTO partyDTO = partyController.updateParty(ID, NAME, authentication);
        assertNotNull(partyDTO);
    }

    @Test
    public void deleteParty() throws Exception {
        PartyDTO partyDTO = partyController.deleteParty(ID, authentication);
        assertEquals(ID, partyDTO.getId());
    }

}