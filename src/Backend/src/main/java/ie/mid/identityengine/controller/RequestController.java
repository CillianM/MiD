package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.InformationRequestDTO;
import ie.mid.identityengine.dto.InformationResponseDTO;
import ie.mid.identityengine.dto.RequestDTO;
import ie.mid.identityengine.dto.UserResponseDTO;
import ie.mid.identityengine.model.Request;
import ie.mid.identityengine.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired
    RequestRepository requestRepository;

    @GetMapping(value = "{id}")
    @ResponseBody
    public RequestDTO createRequest(@PathVariable String id) {
        Request request = requestRepository.findById(id);
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(request.getId());
        requestDTO.setRecipient(request.getRecipient());
        requestDTO.setSender(request.getSender());
        requestDTO.setStatus(request.getStatus());
        return new RequestDTO();
    }

    @PostMapping
    @ResponseBody
    public RequestDTO createRequest(@RequestBody InformationRequestDTO informationRequestDTO) {
        //TODO implement creation of request
        return new RequestDTO();
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public InformationResponseDTO updateRequest(@RequestBody UserResponseDTO userResponseDTO) {
        //TODO implement update of request
        return new InformationResponseDTO();
    }
}
