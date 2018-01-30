package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.IdentityTypeDTO;
import ie.mid.identityengine.enums.FieldType;
import ie.mid.identityengine.model.Field;
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
    private static final String FIELD = "field";
    private static final String FIELDS = "1:2,3:4";
    private IdentityTypeDTO identityTypeDTO = new IdentityTypeDTO();
    private List<IdentityTypeDTO> identityTypeDTOList = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        IdentityType identityType = new IdentityType();
        identityType.setId(ID);
        identityType.setFields(FIELDS);
        List<IdentityType> identityTypeList = new ArrayList<>();
        identityTypeList.add(identityType);
        Field field = new Field(FIELD,FieldType.ADDRESS);
        List<Field> fieldList =new ArrayList<>();
        fieldList.add(field);
        identityTypeDTO.setFields(fieldList);
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
        IdentityTypeDTO identityType = identityTypeController.createIdentityType(identityTypeDTO);
        assertEquals(identityType.getId(), ID);
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
        IdentityTypeDTO identityType = identityTypeController.updateIdentityType(ID, ID, identityTypeDTO);
        assertNotNull(identityType);
    }

    @Test
    public void deleteIdentityType() throws Exception {
        IdentityTypeDTO identityTypeDTO = identityTypeController.deleteIdentityType(ID, ID);
        assertNotNull(identityTypeDTO);
    }
}