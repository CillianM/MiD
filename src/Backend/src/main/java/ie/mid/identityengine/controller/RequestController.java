package ie.mid.identityengine.controller;

import com.google.gson.JsonObject;
import ie.mid.identityengine.dto.InformationRequestDTO;
import ie.mid.identityengine.dto.RequestDTO;
import ie.mid.identityengine.enums.FieldType;
import ie.mid.identityengine.enums.NotificationType;
import ie.mid.identityengine.enums.RequestStatus;
import ie.mid.identityengine.exception.BadRequestException;
import ie.mid.identityengine.exception.ResourceForbiddenException;
import ie.mid.identityengine.exception.ResourceNotFoundException;
import ie.mid.identityengine.model.Party;
import ie.mid.identityengine.model.Request;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.PartyRepository;
import ie.mid.identityengine.repository.RequestRepository;
import ie.mid.identityengine.repository.UserRepository;
import ie.mid.identityengine.service.PushNotificationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    PushNotificationService pushNotificationService;

    private Logger logger = Logger.getLogger(RequestController.class.getName());

    private static final String USER_NOT_EXIST = "User does not exits";

    //Allow requester to see what can be asked
    @GetMapping(value = "/types")
    @ResponseBody
    public List<String> getRequestTypes() {
        return FieldType.getRequestFields();
    }

    @GetMapping(value = "/recipient/{recipientId}")
    @ResponseBody
    public List<RequestDTO> getRecipientRequests(@PathVariable String recipientId, Authentication authentication) {
        List<Request> requests = requestRepository.findByRecipientId(recipientId);
        if (requests == null) throw new ResourceNotFoundException();
        for (Request request : requests) {
            if (!request.getRecipientId().equals(authentication.getName()))
                throw new ResourceForbiddenException(authentication.getName() + " is forbidden from requests");

        }
        return requestListToDTOList(requests);
    }

    @GetMapping(value = "/sender/{senderId}")
    @ResponseBody
    public List<RequestDTO> getSenderRequests(@PathVariable String senderId, Authentication authentication) {
        List<Request> requests = requestRepository.findBySenderId(senderId);
        if (requests == null) throw new ResourceNotFoundException();
        for (Request request : requests) {
            if (!request.getSenderId().equals(authentication.getName()))
                throw new ResourceForbiddenException(authentication.getName() + " is forbidden from requests");

        }
        return requestListToDTOList(requests);
    }

    @PostAuthorize("returnObject.recipientId == authentication.name || returnObject.senderId == authentication.name")
    @GetMapping(value = "{id}")
    @ResponseBody
    public RequestDTO getRequest(@PathVariable String id) {
        Request request = requestRepository.findById(id);
        if (request == null) throw new ResourceNotFoundException();
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
        return requestDTO;
    }

    @PreAuthorize("#informationRequestDTO.senderId == authentication.name")
    @PostMapping
    @ResponseBody
    public RequestDTO createRequest(@RequestBody InformationRequestDTO informationRequestDTO) {
        if (isInvalidRequest(informationRequestDTO)) throw new BadRequestException();
        User recipient = userRepository.findById(informationRequestDTO.getRecipientId());
        if (recipient == null) throw new ResourceNotFoundException(USER_NOT_EXIST);
        User sender = userRepository.findById(informationRequestDTO.getSenderId());
        Party partySender = new Party();
        if (sender == null) partySender = partyRepository.findById(informationRequestDTO.getSenderId());
        if (partySender == null) throw new ResourceNotFoundException(USER_NOT_EXIST);
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

        //Contact the user with the id of the request
        JsonObject messageObject = pushNotificationService.createMessageObject(
                REQUEST_HEADER,
                message);

        JsonObject dataObject = pushNotificationService.createDataObject(NotificationType.REQUEST, new String[]{"fields"},
                new String[]{request.getIdentityTypeFields()}
        );
        try {
            pushNotificationService.sendNotifictaionAndData(recipient.getFcmToken(), messageObject,dataObject);
        } catch (IOException e) {
            logger.error("Error occured sending notification",e);
        }

        //return the new request to the requestee
        return getRequest(request.getId());
    }

    @PreAuthorize("#informationRequestDTO.recipientId == authentication.name")
    @PutMapping(value = "/{id}")
    @ResponseBody
    public RequestDTO updateRequest(@PathVariable String id, @RequestBody InformationRequestDTO informationRequestDTO) {
        Request request = requestRepository.findById(id);
        if (request == null) throw new ResourceNotFoundException(USER_NOT_EXIST);
        if (isInvalidRequest(informationRequestDTO)) throw new BadRequestException();
        request.setIndentityTypeId(informationRequestDTO.getIndentityTypeId());
        request.setIdentityTypeFields(informationRequestDTO.getIdentityTypeFields());

        String message;
        if (informationRequestDTO.getStatus().equals(RequestStatus.REJECTED.toString())) {
            request.setStatus(RequestStatus.REJECTED.toString());
            message = " has rejected your request";
        } else if (informationRequestDTO.getStatus().equals(RequestStatus.RESCINDED.toString())) {
            request.setStatus(RequestStatus.RESCINDED.toString());
            message = " has rescinded their request";
        } else if (informationRequestDTO.getStatus().equals(RequestStatus.ACCEPTED.toString())) {
            request.setUserResponse(informationRequestDTO.getIdentityTypeValues());
            request.setStatus(RequestStatus.ACCEPTED.toString());
            request.setCertificateId(informationRequestDTO.getCertificateId());
            message = " has accepted your request";
        } else {
            throw new BadRequestException();
        }


        //send notification
        User sender = userRepository.findById(informationRequestDTO.getSenderId());

        if(sender != null) {
            User recipient = userRepository.findById(informationRequestDTO.getRecipientId());
            message = recipient.getNickname() + message;
            //Contact the user with the id of the request
            JsonObject messageObject = pushNotificationService.createMessageObject(
                    REQUEST_HEADER,
                    message);

            JsonObject dataObject = pushNotificationService.createDataObject(NotificationType.REQUEST, new String[]{"response"},
                    new String[]{request.getUserResponse()}
            );
            try {
                pushNotificationService.sendNotifictaionAndData(sender.getFcmToken(), messageObject,dataObject);
            } catch (IOException e) {
                logger.error("Error occured sending notification",e);
            }
        }

        requestRepository.save(request);
        return getRequest(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public RequestDTO rescindRequest(@PathVariable String id, Authentication authentication) {
        Request request = requestRepository.findById(id);
        if (request == null) throw new ResourceNotFoundException();
        if (!request.getRecipientId().equals(authentication.getName()))
            throw new ResourceForbiddenException(authentication.getName() + " is forbidden from resource " + id);
        request.setUserResponse(RequestStatus.RESCINDED.toString());
        request.setStatus(RequestStatus.RESCINDED.toString());
        requestRepository.save(request);
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
            return true;
        }
        //Check for invalid request fields
        String[] fieldsRequested = requestDTO.getIdentityTypeFields().split(",");
        List<String> fieldsAvailable = FieldType.getRequestFields();
        for(String field: fieldsRequested){
            if(!fieldsAvailable.contains(field)){
                return true; // asking for illegal field
            }
        }
        return false;
    }

    private String getUserName(String id) {
        User user = userRepository.findById(id);
        if (user != null) {
            return user.getNickname();
        } else {
            return getPartyName(id);
        }
    }

    private String getPartyName(String id) {
        Party party = partyRepository.findById(id);
        if (party != null) {
            return party.getName();
        } else {
            return null;
        }
    }
}
