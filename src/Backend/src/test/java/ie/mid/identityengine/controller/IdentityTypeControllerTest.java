package ie.mid.identityengine.controller;

import ie.mid.identityengine.category.UnitTests;
import ie.mid.identityengine.dto.IdentityTypeDTO;
import ie.mid.identityengine.enums.FieldType;
import ie.mid.identityengine.model.Field;
import ie.mid.identityengine.model.IdentityType;
import ie.mid.identityengine.repository.IdentityTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@Category(UnitTests.class)
@RunWith(MockitoJUnitRunner.class)
public class IdentityTypeControllerTest {

    @InjectMocks
    private IdentityTypeController identityTypeController;

    @Mock
    private IdentityTypeRepository identityTypeRepository;

    private static final String ID = "id";
    private static final String URL = "url";
    private static final String NAME = "name";
    private static final String FIELD = "field";
    private static final String FIELDS = "1:" + FieldType.ADDRESS + ",2:" + FieldType.ADDRESS;
    private IdentityTypeDTO identityTypeDTO = new IdentityTypeDTO();
    private List<IdentityTypeDTO> identityTypeDTOList = new ArrayList<>();
    private Authentication authentication;


    @Before
    public void setUp() throws Exception {
        IdentityType identityType = new IdentityType();
        identityType.setId(ID);
        identityType.setFields(FIELDS);
        identityType.setCoverImg(URL);
        identityType.setIconImg(URL);
        identityType.setName(NAME);
        identityType.setPartyId(ID);
        List<IdentityType> identityTypeList = new ArrayList<>();
        identityTypeList.add(identityType);
        Field field = new Field(FIELD,FieldType.ADDRESS);
        List<Field> fieldList =new ArrayList<>();
        fieldList.add(field);
        identityTypeDTO.setFields(fieldList);
        identityTypeDTO.setCoverImg(URL);
        identityTypeDTO.setIconImg(URL);
        identityTypeDTO.setName(NAME);
        identityTypeDTO.setPartyId(ID);
        identityTypeDTOList.add(identityTypeDTO);

        when(identityTypeRepository.findById(anyString())).thenReturn(identityType);
        when(identityTypeRepository.findLatest()).thenReturn(identityTypeList);
        when(identityTypeRepository.findLatestByPartyId(anyString())).thenReturn(identityTypeList);
        when(identityTypeRepository.save(any(IdentityType.class))).thenReturn(identityType);

        authentication = new UsernamePasswordAuthenticationToken(ID, ID);
    }

    @Test
    public void getIdentityTypes() throws Exception {
        List<IdentityTypeDTO> identityTypeDTOList = identityTypeController.getIdentityTypes();
        assertFalse(identityTypeDTOList.isEmpty());
    }

    @Test
    public void createIdentityType() throws Exception {
        IdentityTypeDTO identityType = identityTypeController.createIdentityType(identityTypeDTO);
        assertEquals(ID, identityType.getId());
    }

    @Test
    public void getPartyIdentityTypes() throws Exception {
        List<IdentityTypeDTO> identityTypeDTOList = identityTypeController.getPartyIdentityTypes(ID);
        assertFalse(identityTypeDTOList.isEmpty());
    }

    @Test
    public void getIdentityType() throws Exception {
        IdentityTypeDTO identityTypeDTO = identityTypeController.getIdentityType(ID);
        assertNotNull(identityTypeDTO);
    }

    @Test
    public void updateIdentityType() throws Exception {
        IdentityTypeDTO identityType = identityTypeController.updateIdentityType(ID, identityTypeDTO);
        assertNotNull(identityType);
    }

    @Test
    public void updateIdentityTypeCosmetic() throws Exception {
        Field field1 = new Field("1", FieldType.ADDRESS);
        Field field2 = new Field("2", FieldType.ADDRESS);
        List<Field> fieldList = new ArrayList<>();
        fieldList.add(field1);
        fieldList.add(field2);
        identityTypeDTO.setFields(fieldList);
        IdentityTypeDTO identityType = identityTypeController.updateIdentityType(ID, identityTypeDTO);
        assertNotNull(identityType);
    }

    @Test
    public void deleteIdentityType() throws Exception {
        IdentityTypeDTO identityTypeDTO = identityTypeController.deleteIdentityType(ID, authentication);
        assertNotNull(identityTypeDTO);
    }
}