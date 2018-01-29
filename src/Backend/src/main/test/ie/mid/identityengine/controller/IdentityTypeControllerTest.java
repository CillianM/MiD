package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.IdentityTypeDTO;
import ie.mid.identityengine.model.IdentityType;
import ie.mid.identityengine.repository.IdentityTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IdentityTypeControllerTest {

    @InjectMocks
    private IdentityTypeController identityTypeController;

    @Mock
    private IdentityTypeRepository identityTypeRepository;

    private static final String ID = "id";

    @Before
    public void setUp() throws Exception {
        IdentityType identityType = new IdentityType();
        identityType.setId(ID);
        List<IdentityType> identityTypeList = new ArrayList<>();
        identityTypeList.add(identityType);
        IdentityTypeDTO identityTypeDTO = Mockito.mock(IdentityTypeDTO.class);
        List<IdentityTypeDTO> identityTypeDTOList = new ArrayList<>();
        identityTypeDTOList.add(identityTypeDTO);

        when(identityTypeRepository.findById(anyString())).thenReturn(identityType);
        when(identityTypeRepository.findLatest()).thenReturn(identityTypeList);
        when(identityTypeRepository.findLatestByPartyId(anyString())).thenReturn(identityTypeList);
        when(identityTypeRepository.save(any(IdentityType.class))).thenReturn(identityType);
    }

    @Test
    public void getIdentityTypes() throws Exception {
        List<IdentityTypeDTO> identityTypeDTOList = identityTypeController.getIdentityTypes();
        assertFalse(identityTypeDTOList.isEmpty());
    }

    @Test
    public void createIdentityType() throws Exception {
        IdentityTypeDTO identityTypeDTO = identityTypeController.createIdentityType(new IdentityTypeDTO());
        assertEquals(identityTypeDTO.getId(), ID);
    }

    @Test
    public void getPartyIdentityTypes() throws Exception {
        List<IdentityTypeDTO> identityTypeDTOList = identityTypeController.getPartyIdentityTypes(ID);
        assertFalse(identityTypeDTOList.isEmpty());
    }

    @Test
    public void getIdentityType() throws Exception {
        IdentityTypeDTO identityTypeDTO = identityTypeController.getIdentityType(ID, ID);
        assertNotNull(identityTypeDTO);
    }

    @Test
    public void updateIdentityType() throws Exception {
        IdentityTypeDTO identityTypeDTO = identityTypeController.updateIdentityType(ID, ID, new IdentityTypeDTO());
        assertNotNull(identityTypeDTO);
    }

    @Test
    public void deleteIdentityType() throws Exception {
        IdentityTypeDTO identityTypeDTO = identityTypeController.deleteIdentityType(ID, ID);
        assertNotNull(identityTypeDTO);
    }
}