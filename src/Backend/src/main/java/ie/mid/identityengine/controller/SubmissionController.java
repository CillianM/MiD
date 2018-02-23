package ie.mid.identityengine.controller;

import com.google.gson.JsonObject;
import ie.mid.identityengine.dto.SubmissionDTO;
import ie.mid.identityengine.enums.NotificationType;
import ie.mid.identityengine.enums.RequestStatus;
import ie.mid.identityengine.exception.BadRequestException;
import ie.mid.identityengine.exception.HyperledgerErrorException;
import ie.mid.identityengine.exception.ResourceNotFoundException;
import ie.mid.identityengine.model.Certificate;
import ie.mid.identityengine.model.Party;
import ie.mid.identityengine.model.Submission;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.PartyRepository;
import ie.mid.identityengine.repository.SubmissionRepository;
import ie.mid.identityengine.repository.UserRepository;
import ie.mid.identityengine.service.HyperledgerService;
import ie.mid.identityengine.service.PushNotificationService;
import ie.mid.identityengine.service.StorageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/submission")
public class SubmissionController {

    @Autowired
    SubmissionRepository submissionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    HyperledgerService hyperledgerService;

    @Autowired
    StorageService storageService;

    private static final String SUBMISSION_HEADER = "MiD Submission Update";
    private static final Logger log = Logger.getLogger(SubmissionController.class.getName());


    @GetMapping(value = "/party/{partyId}")
    @ResponseBody
    public List<SubmissionDTO> getPartySubmissions(@PathVariable String partyId) {
        List<Submission> submissions = submissionRepository.findByPartyId(partyId);
        if (submissions == null) throw new ResourceNotFoundException();
        return submissionListToDTOList(submissions);
    }

    @GetMapping(value = "/user/{userId}")
    @ResponseBody
    public List<SubmissionDTO> getUserSubmissions(@PathVariable String userId) {
        List<Submission> submissions = submissionRepository.findByUserId(userId);
        if (submissions == null) throw new ResourceNotFoundException();
        return submissionListToDTOList(submissions);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public SubmissionDTO getSubmission(@PathVariable String id) {
        Submission submission = submissionRepository.findById(id);
        if (submission == null) throw new ResourceNotFoundException();
        SubmissionDTO dto = new SubmissionDTO();
        dto.setData(storageService.loadData(submission.getData()));
        dto.setId(submission.getId());
        dto.setUserName(getUserName(submission.getUserId()));
        dto.setUserId(submission.getUserId());
        dto.setPartyName(getPartyName(submission.getPartyId()));
        dto.setPartyId(submission.getPartyId());
        dto.setStatus(submission.getStatus());
        dto.setDate(submission.getCreatedAt().toString());
        return dto;
    }

    @PostMapping
    @ResponseBody
    public SubmissionDTO createSubmission(@RequestBody SubmissionDTO submissionToCreate) {
        if (isInvalidSubmission(submissionToCreate)) throw new BadRequestException();
        Submission submission = new Submission();

        submission.setStatus(RequestStatus.PENDING.toString());
        submission.setUserId(submissionToCreate.getUserId());
        submission.setPartyId(submissionToCreate.getPartyId());
        //Save data to file
        submission.setData(storageService.saveData(submissionToCreate.getData()));
        submission = submissionRepository.save(submission);
        submissionToCreate.setId(submission.getId());
        submissionToCreate.setStatus(submission.getStatus());
        submissionToCreate.setDate(submission.getCreatedAt().toString());
        return submissionToCreate;
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public SubmissionDTO updateSubmission(@PathVariable String id, @RequestBody SubmissionDTO submissionToUpdate) {
        Submission submission = submissionRepository.findById(id);
        if (submission == null) throw new ResourceNotFoundException("Submission does not exist");

        String message;
        if (submissionToUpdate.getStatus().equals(RequestStatus.ACCEPTED.toString())) {
            Certificate certificate = getCertificate(submission);
            if(certificate == null) throw new HyperledgerErrorException("Error updating hyperledger");
            submission.setCertificateId(certificate.getCertId());
            submission.setStatus(submissionToUpdate.getStatus());
            submissionRepository.save(submission);
            message = "Your submission has been accepted";
        }
        else{
            message = "Your submission has been rejected";
        }

        //Contact the user
        User user = userRepository.findById(submissionToUpdate.getUserId());
        if (user == null) throw new ResourceNotFoundException("User does not exist");



        JsonObject messageObject = pushNotificationService.createMessageObject(
                SUBMISSION_HEADER,
                message);
        JsonObject dataObject = pushNotificationService.createDataObject(
                NotificationType.APPLICATION_UPDATE,
                new String[]{
                        "submissionId",
                        "status"
                },
                new String[]{
                        submissionToUpdate.getId(),
                        submissionToUpdate.getStatus()
                }
        );
        try {
            pushNotificationService.sendNotifictaionAndData(user.getFcmToken(),messageObject,dataObject);
        } catch (IOException e) {
            log.error("Error sending FCM message",e);
        }
        return submissionToUpdate;
    }

    private Certificate getCertificate(Submission submission) {
        User user = userRepository.findById(submission.getUserId());
        Party party = partyRepository.findById(submission.getPartyId());
        if(user == null || party == null) return null;
        return hyperledgerService.createCertificate(party.getNetworkId(),user.getNetworkId());
    }

    private List<SubmissionDTO> submissionListToDTOList(List<Submission> submissionList) {
        List<SubmissionDTO> submissionDTOList = new ArrayList<>();
        submissionList.forEach((submission -> {
            SubmissionDTO dto = new SubmissionDTO();
            dto.setData(storageService.loadData(submission.getData()));
            dto.setId(submission.getId());
            dto.setUserName(getUserName(submission.getUserId()));
            dto.setUserId(submission.getUserId());
            dto.setPartyName(getPartyName(submission.getPartyId()));
            dto.setPartyId(submission.getPartyId());
            dto.setStatus(submission.getStatus());
            dto.setDate(submission.getCreatedAt().toString());
            submissionDTOList.add(dto);
        }));
        return submissionDTOList;
    }

    private boolean isInvalidSubmission(SubmissionDTO submissionDTO) {
        return submissionDTO.getPartyId() == null || submissionDTO.getUserId() == null || submissionDTO.getData() == null;
    }

    private String getUserName(String id) {
        User user = userRepository.findById(id);
        if (user != null) {
            return user.getNickname();
        } else {
            return null;
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
