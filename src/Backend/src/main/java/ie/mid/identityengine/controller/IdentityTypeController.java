package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.IdentityTypeDTO;
import ie.mid.identityengine.enums.FieldType;
import ie.mid.identityengine.enums.IdentityTypeStatus;
import ie.mid.identityengine.exception.BadRequestException;
import ie.mid.identityengine.exception.ResourceForbiddenException;
import ie.mid.identityengine.exception.ResourceNotFoundException;
import ie.mid.identityengine.model.Field;
import ie.mid.identityengine.model.IdentityType;
import ie.mid.identityengine.repository.IdentityTypeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/identitytype")
public class IdentityTypeController {

    @Autowired
    IdentityTypeRepository identityTypeRepository;

    private Logger logger = LogManager.getLogger(IdentityTypeController.class);

    @GetMapping()
    @ResponseBody
    public List<IdentityTypeDTO> getIdentityTypes() {
        logger.debug("'GET' request to getIdentityTypes()");
        List<IdentityType> identityTypes = identityTypeRepository.findLatest();
        if (identityTypes == null) {
            logger.error("No identity types found, returning empty list");
            return Collections.emptyList();
        }
        return getDtoList(identityTypes);
    }

    @GetMapping(value = "/fields")
    @ResponseBody
    public List<String> getIdentityTypeFields() {
        return FieldType.getFieldTypeList();
    }

    @PostMapping()
    @ResponseBody
    public IdentityTypeDTO createIdentityType(@RequestBody IdentityTypeDTO identityTypeToCreate) {
        logger.debug("'POST' request to createIdentityType()");
        if (isInvalidIdentityType(identityTypeToCreate)) {
            logger.error("Invalid identity type: " + identityTypeToCreate.toString());
            throw new BadRequestException();
        }

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
        logger.debug("Identity type created, returning...");
        return identityTypeToCreate;

    }

    @GetMapping(value = "/party/{partyId}")
    @ResponseBody
    public List<IdentityTypeDTO> getPartyIdentityTypes(@PathVariable String partyId) {
        logger.debug("'GET' request to getPartyIdentityTypes() for partyId " + partyId);
        List<IdentityType> identityTypes = identityTypeRepository.findLatestByPartyId(partyId);
        if (identityTypes == null) {
            logger.error("No identity types found, returning empty list");
            return Collections.emptyList();
        }
        return getDtoList(identityTypes);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public IdentityTypeDTO getIdentityType(@PathVariable String id) {
        logger.debug("'GET' request to getIdentityType() for id " + id);

        IdentityType identityType = identityTypeRepository.findById(id);
        if (identityType == null) {
            logger.error("No identity type found for id " + id);
            throw new ResourceNotFoundException();
        }

        IdentityTypeDTO dto = new IdentityTypeDTO();
        dto.setFields(getFieldList(identityType.getFields()));
        dto.setId(identityType.getId());
        dto.setPartyId(identityType.getPartyId());
        dto.setStatus(identityType.getStatus());
        dto.setVersionNumber(identityType.getVersionNumber());
        dto.setIconImg(identityType.getIconImg());
        dto.setCoverImg(identityType.getCoverImg());
        dto.setName(identityType.getName());
        logger.debug("Identity type found, returning...");
        return dto;
    }

    @PreAuthorize("#identityTypeDTO.partyId == authentication.name")
    @PutMapping(value = "/{id}")
    @ResponseBody
    public IdentityTypeDTO updateIdentityType(@PathVariable String id, @RequestBody IdentityTypeDTO identityTypeDTO) {
        logger.debug("'PUT' request to updateIdentityType() for id " + id);

        if (isInvalidIdentityType(identityTypeDTO)) {
            logger.error("Invalid identity type: " + identityTypeDTO.toString());
            throw new BadRequestException();
        }
        IdentityType identityType = identityTypeRepository.findById(id);
        if (identityType == null) {
            logger.error("No identity type found for id " + id);
            throw new ResourceNotFoundException();
        }

        //Check if we're doing a non-cosmetic update
        if(identityType.getFields().equals(getFieldString(identityTypeDTO.getFields())) && identityType.getName().equals(identityTypeDTO.getName())){
            logger.debug("Identity type update cosmetic");
            //no need to update version number as its cosmetic
            identityType.setIconImg(identityTypeDTO.getIconImg());
            identityType.setCoverImg(identityTypeDTO.getCoverImg());
            identityTypeRepository.save(identityType);
        } else{
            logger.debug("Non-cosmetic update, Identity type version updated");

            IdentityType updatedIdentityType = new IdentityType();
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
        }
        logger.debug("Identity type updated, returning...");
        return identityTypeDTO;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public IdentityTypeDTO deleteIdentityType(@PathVariable String id, Authentication authentication) {
        logger.debug("'DELETE' request to deleteIdentityType() for id " + id);
        IdentityType identityType = identityTypeRepository.findById(id);
        if (identityType == null) {
            logger.error("No identity type found for id " + id);
            throw new ResourceNotFoundException();
        }
        if (!identityType.getPartyId().equals(authentication.getName())) {
            logger.error("Authentication failure: " + authentication.getName() + " != " + identityType.getPartyId());
            throw new ResourceForbiddenException(authentication.getName() + " is forbidden from resource " + id);
        }
        identityType.setStatus(IdentityTypeStatus.DELETED.toString());
        identityTypeRepository.save(identityType);
        IdentityTypeDTO dto = new IdentityTypeDTO();
        dto.setFields(getFieldList(identityType.getFields()));
        dto.setId(identityType.getId());
        dto.setPartyId(identityType.getPartyId());
        dto.setStatus(identityType.getStatus());
        dto.setVersionNumber(identityType.getVersionNumber());
        logger.debug("Identity type deleted, returning...");
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
        if (identityTypeDTO.getCoverImg() == null || identityTypeDTO.getFields() == null || identityTypeDTO.getIconImg() == null || identityTypeDTO.getPartyId() == null || identityTypeDTO.getName() == null)
            return true;
        List<String> fieldsAvailable = FieldType.getFieldTypeList();
        for (Field field : identityTypeDTO.getFields()) {
            if (!fieldsAvailable.contains(field.getType())) {
                return true; // asking for illegal field
            }
        }
        return false;
    }

}
