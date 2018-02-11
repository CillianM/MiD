package ie.mid.identityengine.controller;

import com.google.gson.JsonObject;
import ie.mid.identityengine.dto.InformationRequestDTO;
import ie.mid.identityengine.dto.RequestDTO;
import ie.mid.identityengine.enums.FieldType;
import ie.mid.identityengine.enums.NotificationType;
import ie.mid.identityengine.enums.RequestStatus;
import ie.mid.identityengine.exception.BadRequestException;
import ie.mid.identityengine.exception.ResourceNotFoundException;
import ie.mid.identityengine.model.Request;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.RequestRepository;
import ie.mid.identityengine.repository.UserRepository;
import ie.mid.identityengine.service.PushNotificationService;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    PushNotificationService pushNotificationService;

    Logger logger = Logger.getLogger(RequestController.class.getName());

    private static final String USER_NOT_EXIST = "User does not exits";

    //Allow requester to see what can be asked
    @GetMapping(value = "/types")
    @ResponseBody
    public List<String> getRequestTypes() {
        return FieldType.getRequestFields();
    }

    @GetMapping(value = "/recipient/{recipientId}")
    @ResponseBody
    public List<RequestDTO> getRecipientRequests(@PathVariable String recipientId) {
        List<Request> requests = requestRepository.findByRecipient(recipientId);
        if (requests == null) throw new ResourceNotFoundException();
        return requestListToDTOList(requests);
    }

    @GetMapping(value = "/sender/{senderId}")
    @ResponseBody
    public List<RequestDTO> getSenderRequests(@PathVariable String senderId) {
        List<Request> requests = requestRepository.findBySender(senderId);
        if (requests == null) throw new ResourceNotFoundException();
        return requestListToDTOList(requests);
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public RequestDTO getRequest(@PathVariable String id) {
        Request request = requestRepository.findById(id);
        if (request == null) throw new ResourceNotFoundException();
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(request.getId());
        requestDTO.setRecipient(request.getRecipient());
        requestDTO.setSender(request.getSender());
        requestDTO.setStatus(request.getStatus());
        requestDTO.setIdentityTypeFields(request.getIdentityTypeFields());
        requestDTO.setIdentityTypeValues(request.getUserResponse());
        requestDTO.setIndentityTypeId(request.getIndentityTypeId());
        return requestDTO;
    }

    @PostMapping
    @ResponseBody
    public RequestDTO createRequest(@RequestBody InformationRequestDTO informationRequestDTO) {
        if (isInvalidRequest(informationRequestDTO)) throw new BadRequestException();
        //Create the request to be tracked
        Request request = new Request();
        request.setRecipient(informationRequestDTO.getRecipientId());
        request.setSender(informationRequestDTO.getSenderId());
        request.setIdentityTypeFields(informationRequestDTO.getIdentityTypeFields());
        request.setIndentityTypeId(informationRequestDTO.getIndentityTypeId());
        request.setStatus(RequestStatus.SUBMITTED.toString());
        request = requestRepository.save(request);

        String[] fields = request.getIdentityTypeFields().split(",");

        User recipient = userRepository.findById(informationRequestDTO.getRecipientId());
        if (recipient == null) throw new ResourceNotFoundException(USER_NOT_EXIST);
        User sender = userRepository.findById(informationRequestDTO.getSenderId());
        if (sender == null) throw new ResourceNotFoundException(USER_NOT_EXIST);


        //Contact the user with the id of the request
        JsonObject messageObject = pushNotificationService.createMessageObject(
                REQUEST_HEADER,
                sender.getNickname() + " has requested information");

        JsonObject dataObject = pushNotificationService.createDataObject(NotificationType.REQUEST, new String[]{"fields"},
               fields
        );
        try {
            pushNotificationService.sendNotifictaionAndData(recipient.getFcmToken(), messageObject,dataObject);
        } catch (IOException e) {
            logger.error("Error occured sending notification",e);
        }
        request.setStatus(RequestStatus.PENDING.toString());
        requestRepository.save(request);

        //return the new request to the requestee
        return getRequest(request.getId());
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public RequestDTO updateRequest(@PathVariable String id, @RequestBody InformationRequestDTO informationRequestDTO) {
        Request request = requestRepository.findById(id);
        if (request == null) throw new ResourceNotFoundException(USER_NOT_EXIST);
        if (isInvalidRequest(informationRequestDTO)) throw new BadRequestException();
        request.setIndentityTypeId(informationRequestDTO.getIndentityTypeId());
        request.setIdentityTypeFields(informationRequestDTO.getIdentityTypeFields());
        request.setUserResponse(informationRequestDTO.getIdentityTypeValues());
        requestRepository.save(request);
        return getRequest(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public RequestDTO rescindRequest(@PathVariable String id) {
        Request request = requestRepository.findById(id);
        if (request == null) throw new ResourceNotFoundException(USER_NOT_EXIST);
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
            requestDTO.setRecipient(request.getRecipient());
            requestDTO.setSender(request.getSender());
            requestDTO.setStatus(request.getStatus());
            requestDTO.setIdentityTypeFields(request.getIdentityTypeFields());
            requestDTO.setIdentityTypeValues(request.getUserResponse());
            requestDTO.setIndentityTypeId(request.getIndentityTypeId());
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
}
