package ie.mid.identityengine.controller;

import com.google.gson.JsonObject;
import ie.mid.identityengine.dto.CertificateDTO;
import ie.mid.identityengine.dto.InformationRequestDTO;
import ie.mid.identityengine.dto.RequestDTO;
import ie.mid.identityengine.enums.ClickAction;
import ie.mid.identityengine.enums.FieldType;
import ie.mid.identityengine.enums.NotificationType;
import ie.mid.identityengine.enums.RequestStatus;
import ie.mid.identityengine.exception.BadRequestException;
import ie.mid.identityengine.exception.ResourceForbiddenException;
import ie.mid.identityengine.exception.ResourceNotFoundException;
import ie.mid.identityengine.model.*;
import ie.mid.identityengine.repository.*;
import ie.mid.identityengine.service.PushNotificationService;
import ie.mid.identityengine.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ie.mid.identityengine.security.KeyUtil.hashString;

@Controller
@RequestMapping("/request")
public class RequestController {

    private static final String REQUEST_HEADER = "MiD Information Request";

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PartyRepository partyRepository;
    @Autowired
    IdentityTypeRepository identityTypeRepository;
    @Autowired
    SubmissionRepository submissionRepository;
    @Autowired
    CertificateController certificateController;
    @Autowired
    StorageService storageService;
    @Autowired
    PushNotificationService pushNotificationService;

    private Logger logger = LoggerFactory.getLogger(RequestController.class.getName());

    private static final String USER_NOT_EXIST = "User does not exits";

    @GetMapping(value = "/recipient/{recipientId}")
    @ResponseBody
    public List<RequestDTO> getRecipientRequests(@PathVariable String recipientId, Authentication authentication) {
        logger.debug("'GET' request for getRecipientRequests() with recipientId " + recipientId);
        List<Request> requests = requestRepository.findByRecipientId(recipientId);
        if (requests == null) {
            logger.error("No request found for recipientId " + recipientId);
            throw new ResourceNotFoundException();
        }
        for (Request request : requests) {
            if (!request.getRecipientId().equals(authentication.getName())) {
                logger.error("Authentication failure: " + authentication.getName() + " != " + request.getRecipientId());
                throw new ResourceForbiddenException(authentication.getName() + " is forbidden from requests");
            }

        }
        return requestListToDTOList(requests);
    }

    @GetMapping(value = "/sender/{senderId}")
    @ResponseBody
    public List<RequestDTO> getSenderRequests(@PathVariable String senderId, Authentication authentication) {
        logger.debug("'GET' request for getSenderRequests() with senderId " + senderId);
        List<Request> requests = requestRepository.findBySenderId(senderId);
        if (requests == null) {
            logger.error("No request found for senderId " + senderId);
            throw new ResourceNotFoundException();
        }
        for (Request request : requests) {
            if (!request.getSenderId().equals(authentication.getName())) {
                logger.error("Authentication failure: " + authentication.getName() + " != " + request.getSenderId());
                throw new ResourceForbiddenException(authentication.getName() + " is forbidden from requests");
            }

        }
        return requestListToDTOList(requests);
    }

    @PostAuthorize("returnObject.recipientId == authentication.name || returnObject.senderId == authentication.name")
    @GetMapping(value = "{id}")
    @ResponseBody
    public RequestDTO getRequest(@PathVariable String id) {
        logger.debug("'GET' request for getSenderRequests() with id " + id);

        Request request = requestRepository.findById(id);
        if (request == null) {
            logger.debug("No request found for id " + id);
            throw new ResourceNotFoundException();
        }
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(request.getId());
        requestDTO.setRecipientName(getUserName(request.getRecipientId()));
        requestDTO.setSenderName(getUserName(request.getSenderId()));
        requestDTO.setRecipientId(request.getRecipientId());
        requestDTO.setSenderId(request.getSenderId());
        requestDTO.setStatus(request.getStatus());
        requestDTO.setIdentityTypeFields(request.getIdentityTypeFields());
        requestDTO.setIdentityTypeValues(request.getUserResponse());
        requestDTO.setIndentityTypeId(request.getIndentityTypeId());
        requestDTO.setCreatedAt(request.getCreatedAt().toString());
        requestDTO.setCertificateId(request.getCertificateId());
        logger.debug("Request found, returning...");
        return requestDTO;
    }

    @PreAuthorize("#informationRequestDTO.senderId == authentication.name")
    @PostMapping
    @ResponseBody
    public RequestDTO createRequest(@RequestBody InformationRequestDTO informationRequestDTO) {
        logger.debug("'POST' request for createRequest()");
        if (isInvalidRequest(informationRequestDTO)) {
            logger.error("Invalid request: " + informationRequestDTO.toString());
            throw new BadRequestException();
        }
        User recipient = userRepository.findById(informationRequestDTO.getRecipientId());
        if (recipient == null) {
            logger.error("Recipient user with id: " + informationRequestDTO.getRecipientId() + " does not exist");
            throw new ResourceNotFoundException(USER_NOT_EXIST);
        }
        User sender = userRepository.findById(informationRequestDTO.getSenderId());
        Party partySender = new Party();
        if (sender == null) {
            logger.debug("No user found for senderId: " + informationRequestDTO.getSenderId() + ", will attempt to find party");
            partySender = partyRepository.findById(informationRequestDTO.getSenderId());
        }
        if (partySender == null) {
            logger.error("No party exists with id " + informationRequestDTO.getSenderId());
            throw new ResourceNotFoundException(USER_NOT_EXIST);
        }
        IdentityType identityType = identityTypeRepository.findById(informationRequestDTO.getIndentityTypeId());
        if (identityType == null) {
            logger.error("No identitytype exists with id " + informationRequestDTO.getIndentityTypeId());
            throw new ResourceNotFoundException("No identitytype exists with id " + informationRequestDTO.getIndentityTypeId());
        }
        if (containsInvalidFields(informationRequestDTO.getIdentityTypeFields(), identityType)) {
            logger.error("Invalid fields for identity type: " + informationRequestDTO.getIdentityTypeFields());
            throw new BadRequestException();
        }
        //Create the request to be tracked
        Request request = new Request();
        request.setRecipientId(recipient.getId());
        String message = " has requested information";
        if(sender == null) {
            message = partySender.getName() + message;
            request.setSenderId(partySender.getId());
        } else {
            message = sender.getNickname() + message;
            request.setSenderId(sender.getId());
        }
        request.setIdentityTypeFields(informationRequestDTO.getIdentityTypeFields());
        request.setIndentityTypeId(informationRequestDTO.getIndentityTypeId());
        request.setStatus(RequestStatus.PENDING.toString());
        request = requestRepository.save(request);
        logger.debug("Request saved, sending notification to recipient...");

        //Contact the user with the id of the request
        JsonObject messageObject = pushNotificationService.createMessageObject(
                REQUEST_HEADER,
                message,
                ClickAction.OPEN_REQUEST.toString());

        JsonObject dataObject = pushNotificationService.createDataObject(
                NotificationType.REQUEST,
                new String[]{
                        "requestId",
                        "serverId"
                },
                new String[]{
                        request.getId(),
                        recipient.getId()
                }
        );
        try {
            pushNotificationService.sendNotifictaionAndData(recipient.getFcmToken(), messageObject,dataObject);
        } catch (IOException e) {
            logger.error("Error occured sending notification",e);
        }
        logger.debug("Notification attempted, returning...");
        //return the new request to the requestee
        return getRequest(request.getId());
    }

    @PreAuthorize("#informationRequestDTO.recipientId == authentication.name")
    @PutMapping(value = "/{id}")
    @ResponseBody
    public RequestDTO updateRequest(@PathVariable String id, @RequestBody InformationRequestDTO informationRequestDTO) {
        logger.debug("'PUT' request for updateRequest() with id " + id);

        Request request = requestRepository.findById(id);
        if (request == null) {
            logger.error("No requests exist for id " + id);
            throw new ResourceNotFoundException("No requests exists for id " + id);
        }
        IdentityType identityType = identityTypeRepository.findById(informationRequestDTO.getIndentityTypeId());
        if (identityType == null) {
            logger.error("No identity type exists with id " + informationRequestDTO.getIndentityTypeId());
            throw new ResourceNotFoundException("No identity type exists with id " + informationRequestDTO.getIndentityTypeId());
        }
        if (isInvalidRequest(informationRequestDTO)) {
            logger.error("Invalid request: " + informationRequestDTO.toString());
            throw new BadRequestException();
        }
        if (containsInvalidFields(informationRequestDTO.getIdentityTypeFields(), identityType)) {
            logger.error("Invalid fields for identity type: " + informationRequestDTO.getIdentityTypeFields());
            throw new BadRequestException();
        }
        request.setIdentityTypeFields(informationRequestDTO.getIdentityTypeFields());

        String message;
        if (informationRequestDTO.getStatus().equals(RequestStatus.REJECTED.toString())) {
            request.setStatus(RequestStatus.REJECTED.toString());
            message = " has rejected your request";
        } else if (informationRequestDTO.getStatus().equals(RequestStatus.RESCINDED.toString())) {
            request.setStatus(RequestStatus.RESCINDED.toString());
            message = " has rescinded their request";
        } else if (informationRequestDTO.getStatus().equals(RequestStatus.ACCEPTED.toString())) {
            CertificateDTO certificate = certificateController.getCertificate(informationRequestDTO.getCertificateId());
            if (certificate == null) {
                logger.error("No certificate exists with id " + informationRequestDTO.getCertificateId());
                throw new ResourceNotFoundException("No certificate exists with id " + informationRequestDTO.getCertificateId());
            }
            if (isInvalidIdentityFields(informationRequestDTO, certificate)) {
                logger.error("Identity fields don't match whats on file " + informationRequestDTO);
                throw new BadRequestException("Identity fields don't match whats on file " + informationRequestDTO);
            }
            request.setUserResponse(informationRequestDTO.getIdentityTypeValues());
            request.setStatus(RequestStatus.ACCEPTED.toString());
            request.setCertificateId(informationRequestDTO.getCertificateId());
            message = " has accepted your request";
        } else {
            logger.error("Bad status for request: " + informationRequestDTO.toString());
            throw new BadRequestException();
        }
        requestRepository.save(request);
        logger.debug("Request updated, sending notification to sender...");
        //send notification
        User sender = userRepository.findById(informationRequestDTO.getSenderId());

        if(sender != null) {
            User recipient = userRepository.findById(informationRequestDTO.getRecipientId());
            message = recipient.getNickname() + message;
            //Contact the user with the id of the request
            JsonObject messageObject = pushNotificationService.createMessageObject(
                    REQUEST_HEADER,
                    message,
                    ClickAction.OPEN_REQUEST.toString());

            JsonObject dataObject = pushNotificationService.createDataObject(
                    NotificationType.REQUEST,
                    new String[]{
                            "requestId",
                            "serverId"
                    },
                    new String[]{
                            request.getId(),
                            sender.getId()
                    }
            );
            try {
                pushNotificationService.sendNotifictaionAndData(sender.getFcmToken(), messageObject,dataObject);
            } catch (IOException e) {
                logger.error("Error occurred sending notification", e);
            }
        }
        logger.debug("Notification attempted, returning...");

        return getRequest(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public RequestDTO rescindRequest(@PathVariable String id, Authentication authentication) {
        logger.debug("'DELETE' request to rescindRequest() with id " + id);
        Request request = requestRepository.findById(id);
        if (request == null) {
            logger.error("No request found for id " + id);
            throw new ResourceNotFoundException();
        }
        if (!request.getRecipientId().equals(authentication.getName())) {
            logger.error("Authentication failure: " + authentication.getName() + " != " + request.getRecipientId());
            throw new ResourceForbiddenException(authentication.getName() + " is forbidden from resource " + id);
        }
        request.setUserResponse(RequestStatus.RESCINDED.toString());
        request.setStatus(RequestStatus.RESCINDED.toString());
        requestRepository.save(request);
        logger.debug("Request deleted, returning...");
        return getRequest(id);
    }

    private List<RequestDTO> requestListToDTOList(List<Request> requestList) {
        List<RequestDTO> requestDTOList = new ArrayList<>();
        requestList.forEach((request -> {
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setId(request.getId());
            requestDTO.setRecipientId(request.getRecipientId());
            requestDTO.setSenderId(request.getSenderId());
            requestDTO.setRecipientName(getUserName(request.getRecipientId()));
            requestDTO.setSenderName(getUserName(request.getSenderId()));
            requestDTO.setStatus(request.getStatus());
            requestDTO.setIdentityTypeFields(request.getIdentityTypeFields());
            requestDTO.setIdentityTypeValues(request.getUserResponse());
            requestDTO.setIndentityTypeId(request.getIndentityTypeId());
            requestDTO.setCreatedAt(request.getCreatedAt().toString());
            requestDTOList.add(requestDTO);
        }));
        return requestDTOList;
    }

    private boolean isInvalidRequest(InformationRequestDTO requestDTO) {
        if( requestDTO.getRecipientId() == null || requestDTO.getSenderId() == null || requestDTO.getIndentityTypeId() == null || requestDTO.getIdentityTypeFields() == null){
            logger.error("InformationRequestDTO contains null parameters: " + requestDTO.toString());
            return true;
        }
        if (requestDTO.getRecipientId().isEmpty() || requestDTO.getSenderId().isEmpty() || requestDTO.getIndentityTypeId().isEmpty() || requestDTO.getIdentityTypeFields().isEmpty()) {
            logger.error("InformationRequestDTO contains empty parameters: " + requestDTO.toString());
            return true;
        }
        //Check for invalid request fields
        String[] fieldsRequested = requestDTO.getIdentityTypeFields().split(",");
        List<String> fieldsAvailable = FieldType.getFieldTypeList();
        for(String field: fieldsRequested){
            if(!fieldsAvailable.contains(field)){
                logger.error("InformationRequestDTO contains illegal field: " + field.toString());
                return true; // asking for illegal field
            }
        }
        return false;
    }

    private boolean isInvalidIdentityFields(InformationRequestDTO informationRequestDTO, CertificateDTO certificateDTO) {
        Submission submission = submissionRepository.findByCertificateId(certificateDTO.getId());
        if (submission == null) {
            logger.error("No submission found for certifcate id: " + certificateDTO.getId());
            return true;
        }
        if (!submission.getSubmissionHash().equals(certificateDTO.getSubmissionHash())) {
            logger.error("Hash for submission and certificate are not equal: " + submission.getSubmissionHash() + " != " + certificateDTO.getSubmissionHash());
            return true;
        }

        String[] responseValues = informationRequestDTO.getIdentityTypeValues().split(",");
        String[] types = informationRequestDTO.getIdentityTypeFields().split(",");
        List<String> submissionTypeHashes = new ArrayList<>();
        for (int i = 0; i < responseValues.length; i++) {
            String type = types[i] + ":" + responseValues[i];
            String b64SubmissionEntry = hashString(type);
            submissionTypeHashes.add(b64SubmissionEntry);
        }
        String[] submissionEntries = submission.getSubmissionHash().split(",");
        return !(Arrays.asList(submissionEntries).containsAll(submissionTypeHashes));
    }

    private boolean containsInvalidFields(String identityTypeFields, IdentityType identityType) {
        String[] fields = identityType.getFieldsArray();
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].substring(fields[i].indexOf(":") + 1, fields[i].length());
        }
        String[] potentialFields = identityTypeFields.split(",");
        return !(Arrays.asList(fields).containsAll(Arrays.asList(potentialFields)));
    }

    private String getUserName(String id) {
        logger.debug("Finding user with id " + id);
        User user = userRepository.findById(id);
        if (user != null) {
            return user.getNickname();
        } else {
            logger.debug("No user found for id " + id + ", trying party");
            return getPartyName(id);
        }
    }

    private String getPartyName(String id) {
        logger.debug("Finding party with id " + id);
        Party party = partyRepository.findById(id);
        if (party != null) {
            return party.getName();
        } else {
            logger.debug("No party found for id " + id + ", returning null");
            return null;
        }
    }
}
