package ie.mid.identityengine.controller;

import com.google.gson.JsonObject;
import ie.mid.identityengine.dto.SubmissionDTO;
import ie.mid.identityengine.enums.NotificationType;
import ie.mid.identityengine.enums.RequestStatus;
import ie.mid.identityengine.exception.*;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    private static final Logger logger = LogManager.getLogger(SubmissionController.class.getName());


    @GetMapping(value = "/party/{partyId}")
    @ResponseBody
    public List<SubmissionDTO> getPartySubmissions(@PathVariable String partyId, Authentication authentication) {
        logger.debug("'GET' request to getPartySubmissions() for partyId " + partyId);
        List<Submission> submissions = submissionRepository.findByPartyId(partyId);
        if (submissions == null) {
            logger.debug("No submissions found for partyId " + partyId);
            return Collections.emptyList();
        }
        for (Submission submission : submissions) {
            if (!submission.getPartyId().equals(authentication.getName())) {
                logger.error("Authentication failure: " + authentication.getName() + " != " + submission.getPartyId());
                throw new ResourceForbiddenException(authentication.getName() + " is forbidden from requests");
            }

        }
        logger.debug("Submissions found, returning...");
        return submissionListToDTOList(submissions);
    }

    @GetMapping(value = "/user/{userId}")
    @ResponseBody
    public List<SubmissionDTO> getUserSubmissions(@PathVariable String userId, Authentication authentication) {
        logger.debug("'GET' request to getPartySubmissions() for userId " + userId);

        List<Submission> submissions = submissionRepository.findByUserId(userId);
        if (submissions == null) {
            logger.debug("No submissions found for userId " + userId);
            return Collections.emptyList();
        }
        for (Submission submission : submissions) {
            if (!submission.getUserId().equals(authentication.getName())) {
                logger.error("Authentication failure: " + authentication.getName() + " != " + submission.getUserId());
                throw new ResourceForbiddenException(authentication.getName() + " is forbidden from requests");
            }

        }
        logger.debug("Submissions found, returning...");

        return submissionListToDTOList(submissions);
    }

    @PostAuthorize("returnObject.userId == authentication.name || returnObject.partyId == authentication.name")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public SubmissionDTO getSubmission(@PathVariable String id) {
        logger.debug("'GET' request to getSubmission() for id " + id);

        Submission submission = submissionRepository.findById(id);
        if (submission == null) {
            logger.error("No submission found for id " + id);
            throw new ResourceNotFoundException();
        }
        SubmissionDTO dto = new SubmissionDTO();
        dto.setData(storageService.loadData(submission.getData()));
        dto.setId(submission.getId());
        dto.setUserName(getUserName(submission.getUserId()));
        dto.setUserId(submission.getUserId());
        dto.setPartyName(getPartyName(submission.getPartyId()));
        dto.setPartyId(submission.getPartyId());
        dto.setStatus(submission.getStatus());
        dto.setDate(submission.getCreatedAt().toString());
        dto.setCertId(submission.getCertificateId());
        logger.debug("Got submission, returning...");
        return dto;
    }

    @PreAuthorize("#submissionToCreate.userId == authentication.name")
    @PostMapping
    @ResponseBody
    public SubmissionDTO createSubmission(@RequestBody SubmissionDTO submissionToCreate) {
        logger.debug("'POST' request to createSubmission()");
        if (isInvalidSubmission(submissionToCreate)) {
            logger.error("Invalid submission: " + submissionToCreate.toString());
            throw new BadRequestException();
        }
        User user = userRepository.findById(submissionToCreate.getUserId());
        Party party = partyRepository.findById(submissionToCreate.getPartyId());
        if (user == null || party == null) {
            logger.error("User or Party not found for submission: " + submissionToCreate.toString());
            throw new ResourceNotFoundException();
        }

        Submission submission = new Submission();
        submission.setStatus(RequestStatus.PENDING.toString());
        submission.setUserId(submissionToCreate.getUserId());
        submission.setPartyId(submissionToCreate.getPartyId());
        //Save data to file
        submission.setData(storageService.saveData(submissionToCreate.getData()));
        if (submission.getData() == null) {
            throw new ServerErrorException("Error saving data to server");
        }

        submission = submissionRepository.save(submission);
        submissionToCreate.setId(submission.getId());
        submissionToCreate.setStatus(submission.getStatus());
        submissionToCreate.setDate(submission.getCreatedAt().toString());
        logger.debug("Submission created, returning...");

        return submissionToCreate;
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public SubmissionDTO updateSubmission(@PathVariable String id, @RequestBody SubmissionDTO submissionToUpdate, Authentication authentication) {
        logger.debug("'PUT' request to updateSubmission() for id " + id);

        Submission submission = submissionRepository.findById(id);
        if (submission == null) {
            logger.error("No submission exists for id " + id);
            throw new ResourceNotFoundException("Submission does not exist");
        }
        if (!submission.getPartyId().equals(authentication.getName())) {
            logger.error("Authentication failure: " + authentication.getName() + " != " + submission.getPartyId());
            throw new ResourceForbiddenException(authentication.getName() + " is forbidden from resource " + id);
        }
        User user = userRepository.findById(submissionToUpdate.getUserId());
        if (user == null) {
            logger.debug("No user exists for submission with id " + submissionToUpdate.getId());
            throw new ResourceNotFoundException("User does not exist");
        }

        String message;
        if (submissionToUpdate.getStatus().equals(RequestStatus.ACCEPTED.toString())) {
            logger.debug("Submission accepted, creating certificate...");
            Certificate certificate = getCertificate(submission);
            if (certificate == null) {
                logger.error("Error creating certificate in hyperledger");
                throw new HyperledgerErrorException("Error updating hyperledger");
            }
            submission.setCertificateId(certificate.getCertId());
            submission.setStatus(submissionToUpdate.getStatus());
            submissionRepository.save(submission);
            message = "Your submission has been accepted";
        } else{
            message = "Your submission has been rejected";
        }

        logger.debug("Submission updated, notifying user...");
        //Contact the user
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
                        id,
                        submissionToUpdate.getStatus()
                }
        );
        try {
            pushNotificationService.sendNotifictaionAndData(user.getFcmToken(),messageObject,dataObject);
        } catch (IOException e) {
            logger.error("Error sending FCM message", e);
        }
        logger.debug("Attempted notification, returning...");
        return getSubmission(id);
    }

    private Certificate getCertificate(Submission submission) {
        User user = userRepository.findById(submission.getUserId());
        Party party = partyRepository.findById(submission.getPartyId());
        if (user == null) {
            logger.error("No user exists with id " + submission.getUserId());
            return null;
        }
        if (party == null) {
            logger.error("No party exists with id " + submission.getPartyId());
            return null;
        }
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
            dto.setCertId(submission.getCertificateId());
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
            logger.error("No user exists with id " + id);
            return null;
        }
    }

    private String getPartyName(String id) {
        Party party = partyRepository.findById(id);
        if (party != null) {
            return party.getName();
        } else {
            logger.error("No party exists with id " + id);
            return null;
        }
    }
}
