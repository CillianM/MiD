package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.InformationRequestDTO;
import ie.mid.identityengine.dto.RequestDTO;
import ie.mid.identityengine.enums.RequestStatus;
import ie.mid.identityengine.model.Request;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.RequestRepository;
import ie.mid.identityengine.repository.UserRepository;
import ie.mid.identityengine.service.PushNotificationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/request")
public class RequestController {

    private static final String REQUESTHEADER = "Information request";
    private static final String REQUESTBODY = "This is a request for information from one user to another";

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PushNotificationService pushNotificationService;

    @GetMapping(value = "{id}")
    @ResponseBody
    public RequestDTO getRequest(@PathVariable String id) {
        Request request = requestRepository.findById(id);
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(request.getId());
        requestDTO.setRecipient(request.getRecipient());
        requestDTO.setSender(request.getSender());
        requestDTO.setStatus(request.getStatus());
        requestDTO.setIdentityTypeFields(request.getIdentityTypeFields());
        requestDTO.setIdentityTypeValues(request.getUserResponse());
        requestDTO.setIndentityTypeId(request.getIndentityTypeId());
        return new RequestDTO();
    }

    @PostMapping
    @ResponseBody
    public RequestDTO createRequest(@RequestBody InformationRequestDTO informationRequestDTO) {
        //TODO implement creation of request

        //Create the request to be tracked
        Request request = new Request();
        request.setRecipient(informationRequestDTO.getRecipientId());
        request.setSender(informationRequestDTO.getSenderId());
        request.setIdentityTypeFields(informationRequestDTO.getIdentityTypeFields());
        request.setIndentityTypeId(informationRequestDTO.getIndentityTypeId());
        request.setStatus(RequestStatus.SUBMITTED.toString());
        request = requestRepository.save(request);

        String[] fields = request.getIdentityTypeFields().split(",");

        User user = userRepository.findById(informationRequestDTO.getRecipientId());

        //Contact the user with the id of the request
        JSONObject notificationObject = pushNotificationService.createNotification(
                REQUESTHEADER,
                new String[]{"fields"},
                new Object[]{fields}
        );
        pushNotificationService.sendNotification(user.getFcmToken(), notificationObject);
        request.setStatus(RequestStatus.PENDING.toString());
        requestRepository.save(request);

        //return the new request to the requestee
        return getRequest(request.getId());
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public RequestDTO updateRequest(@PathVariable String id, @RequestBody InformationRequestDTO informationRequestDTO) {
        Request request = requestRepository.findById(id);
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
        request.setUserResponse("REMOVED");
        request.setStatus(RequestStatus.RESCINDED.toString());
        requestRepository.save(request);
        return getRequest(id);
    }
}
