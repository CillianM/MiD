package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.KeyDTO;
import ie.mid.identityengine.dto.PartyDTO;
import ie.mid.identityengine.model.Party;
import ie.mid.identityengine.repository.PartyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

    private static final String ID = "id";

    @Before
    public void setUp() throws Exception {

        Party party = new Party();
        party.setId(ID);
        List<Party> parties = new ArrayList<>();
        parties.add(party);
        KeyDTO key = new KeyDTO();
        key.setId(ID);
        when(partyRepository.save(any(Party.class))).thenReturn(party);
        when(partyRepository.findById(anyString())).thenReturn(party);
        when(partyRepository.findAll()).thenReturn(parties);
        when(keyController.createKey(any(KeyDTO.class))).thenReturn(key);
        when(keyController.getKey(anyString())).thenReturn(key);
    }

    @Test
    public void getParties() throws Exception {
        List<PartyDTO> parties = partyController.getParties();
        assertFalse(parties.isEmpty());
    }

    @Test
    public void getParty() throws Exception {
        PartyDTO partyDTO = partyController.getParty(ID);
        assertEquals(partyDTO.getId(), ID);
    }

    @Test
    public void createParty() throws Exception {
        PartyDTO partyDTO = partyController.createParty(new PartyDTO());
        assertEquals(partyDTO.getId(), ID);
    }

    @Test
    public void updateParty() throws Exception {
        PartyDTO partyDTO = partyController.updateParty(ID, new PartyDTO());
        assertNotNull(partyDTO);
    }

    @Test
    public void deleteParty() throws Exception {
        PartyDTO partyDTO = partyController.deleteParty(ID);
        assertEquals(partyDTO.getId(), ID);
    }

}