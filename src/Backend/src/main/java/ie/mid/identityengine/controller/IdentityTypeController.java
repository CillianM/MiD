package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.IdentityTypeDTO;
import ie.mid.identityengine.enums.FieldType;
import ie.mid.identityengine.enums.IdentityTypeStatus;
import ie.mid.identityengine.exception.BadRequestException;
import ie.mid.identityengine.exception.ResourceNotFoundException;
import ie.mid.identityengine.model.Field;
import ie.mid.identityengine.model.IdentityType;
import ie.mid.identityengine.repository.IdentityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/identitytype")
public class IdentityTypeController {

    @Autowired
    IdentityTypeRepository identityTypeRepository;

    @GetMapping()
    @ResponseBody
    public List<IdentityTypeDTO> getIdentityTypes() {
        List<IdentityType> identityTypes = identityTypeRepository.findLatest();
        if (identityTypes == null) throw new ResourceNotFoundException();
        return getDtoList(identityTypes);
    }

    @PostMapping()
    @ResponseBody
    public IdentityTypeDTO createIdentityType(@RequestBody IdentityTypeDTO identityTypeToCreate) {
        if (isInvalidIdentityType(identityTypeToCreate)) throw new BadRequestException();

        IdentityType identityType = new IdentityType();
        identityType.setFields(getFieldString(identityTypeToCreate.getFields()));
        identityType.setPartyId(identityTypeToCreate.getPartyId());
        identityType.setStatus(IdentityTypeStatus.ACTIVE.toString());
        identityType.setName(identityTypeToCreate.getName());
        identityType.setCoverImg(identityTypeToCreate.getCoverImg());
        identityType.setIconImg(identityTypeToCreate.getIconImg());
        identityType.setVersionNumber(1);
        identityType = identityTypeRepository.save(identityType);
        identityTypeToCreate.setId(identityType.getId());
        identityTypeToCreate.setVersionNumber(identityType.getVersionNumber());
        identityTypeToCreate.setStatus(identityType.getStatus());
        return identityTypeToCreate;

    }

    @GetMapping(value = "/{partyId}")
    @ResponseBody
    public List<IdentityTypeDTO> getPartyIdentityTypes(@PathVariable String partyId) {
        List<IdentityType> identityTypes = identityTypeRepository.findLatestByPartyId(partyId);
        if (identityTypes == null) throw new ResourceNotFoundException();
        return getDtoList(identityTypes);
    }

    @GetMapping(value = "/{partyId}/{id}")
    @ResponseBody
    public IdentityTypeDTO getIdentityType(@PathVariable String partyId, @PathVariable String id) {

        IdentityType identityType = identityTypeRepository.findById(id);
        if (identityType == null) throw new ResourceNotFoundException();

        IdentityTypeDTO dto = new IdentityTypeDTO();
        dto.setFields(getFieldList(identityType.getFields()));
        dto.setId(identityType.getId());
        dto.setPartyId(identityType.getPartyId());
        dto.setStatus(identityType.getStatus());
        dto.setVersionNumber(identityType.getVersionNumber());
        dto.setIconImg(identityType.getIconImg());
        dto.setCoverImg(identityType.getCoverImg());
        dto.setName(identityType.getName());
        return dto;
    }

    @PutMapping(value = "/{partyId}/{id}")
    @ResponseBody
    public IdentityTypeDTO updateIdentityType(@PathVariable String partyId, @PathVariable String id, @RequestBody IdentityTypeDTO identityTypeDTO) {
        if (isInvalidIdentityType(identityTypeDTO)) throw new BadRequestException();

        IdentityType updatedIdentityType = new IdentityType();
        IdentityType identityType = identityTypeRepository.findById(id);
        if (identityType == null) throw new ResourceNotFoundException();

        updatedIdentityType.setPartyId(identityTypeDTO.getPartyId());
        updatedIdentityType.setName(identityTypeDTO.getName());
        updatedIdentityType.setIconImg(identityTypeDTO.getIconImg());
        updatedIdentityType.setCoverImg(identityTypeDTO.getCoverImg());
        updatedIdentityType.setFields(getFieldString(identityTypeDTO.getFields()));
        updatedIdentityType.setStatus(IdentityTypeStatus.ACTIVE.toString());
        updatedIdentityType.setVersionNumber(identityType.getVersionNumber() + 1);
        identityType.setStatus(IdentityTypeStatus.UPGRADED.toString());
        identityTypeRepository.save(updatedIdentityType);
        identityTypeDTO.setId(updatedIdentityType.getId());
        identityTypeDTO.setStatus(updatedIdentityType.getStatus());
        identityTypeDTO.setVersionNumber(updatedIdentityType.getVersionNumber());
        identityTypeDTO.setPartyId(updatedIdentityType.getPartyId());
        identityTypeDTO.setFields(getFieldList(updatedIdentityType.getFields()));
        identityTypeDTO.setName(updatedIdentityType.getName());
        identityTypeDTO.setIconImg(updatedIdentityType.getIconImg());
        identityTypeDTO.setCoverImg(updatedIdentityType.getCoverImg());
        return identityTypeDTO;

    }

    @DeleteMapping(value = "/{partyId}/{id}")
    @ResponseBody
    public IdentityTypeDTO deleteIdentityType(@PathVariable String partyId, @PathVariable String id) {
        IdentityType identityType = identityTypeRepository.findById(id);
        if (identityType == null) throw new ResourceNotFoundException();
        identityType.setStatus(IdentityTypeStatus.DELETED.toString());
        identityTypeRepository.save(identityType);
        IdentityTypeDTO dto = new IdentityTypeDTO();
        dto.setFields(getFieldList(identityType.getFields()));
        dto.setId(identityType.getId());
        dto.setPartyId(identityType.getPartyId());
        dto.setStatus(identityType.getStatus());
        dto.setVersionNumber(identityType.getVersionNumber());
        return dto;
    }

    private List<IdentityTypeDTO> getDtoList(List<IdentityType> list) {
        List<IdentityTypeDTO> identityTypeDTOList = new ArrayList<>();
        list.forEach((identityType -> {
            IdentityTypeDTO dto = new IdentityTypeDTO();
            dto.setFields(getFieldList(identityType.getFields()));
            dto.setId(identityType.getId());
            dto.setPartyId(identityType.getPartyId());
            dto.setName(identityType.getName());
            dto.setCoverImg(identityType.getCoverImg());
            dto.setIconImg(identityType.getIconImg());
            dto.setStatus(identityType.getStatus());
            dto.setVersionNumber(identityType.getVersionNumber());
            identityTypeDTOList.add(dto);
        }));
        return identityTypeDTOList;
    }

    private List<Field> getFieldList(String fieldString){
        String [] fieldArray = fieldString.split(",");
        List<Field> fieldList = new ArrayList<>();
        for(String field:fieldArray){
            FieldType fieldType = FieldType.findFieldType(field.substring(field.indexOf(':') + 1));
            if(fieldType != null) {
                fieldList.add(new Field(field.substring(0, field.indexOf(':')), fieldType));
            }
        }
        return fieldList;
    }

    private String getFieldString(List<Field> fieldList){
        StringBuilder stringBuilder = new StringBuilder();
        for(Field field: fieldList){
            FieldType fieldType = FieldType.findFieldType(field.getType());
            if(fieldType != null) {
                String fieldString = field.getName() + ":" + fieldType.toString() + ",";
                stringBuilder.append(fieldString);
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }


    private boolean isInvalidIdentityType(IdentityTypeDTO identityTypeDTO) {
        return identityTypeDTO.getCoverImg() == null || identityTypeDTO.getFields() == null || identityTypeDTO.getIconImg() == null || identityTypeDTO.getPartyId() == null || identityTypeDTO.getName() == null;
    }
}
