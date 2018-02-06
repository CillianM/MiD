package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.SubmissionDTO;
import ie.mid.identityengine.enums.NotificationType;
import ie.mid.identityengine.enums.RequestStatus;
import ie.mid.identityengine.exception.BadRequestException;
import ie.mid.identityengine.exception.ResourceNotFoundException;
import ie.mid.identityengine.model.Submission;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.SubmissionRepository;
import ie.mid.identityengine.repository.UserRepository;
import ie.mid.identityengine.service.PushNotificationService;
import ie.mid.identityengine.service.StorageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    PushNotificationService pushNotificationService;

    @Autowired
    StorageService storageService;

    private static final String SUBMISSION_HEADER = "Submission Update";

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
        dto.setUserId(submission.getUserId());
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
        submission.setStatus(submissionToUpdate.getStatus());
        submissionRepository.save(submission);


        if (submissionToUpdate.getStatus().equals(RequestStatus.ACCEPTED.toString())) {
            //Contact the user
            User user = userRepository.findById(submissionToUpdate.getUserId());
            if (user == null) throw new ResourceNotFoundException("User does not exist");


            //Contact the user with the id of the request
            JSONObject notificationObject = pushNotificationService.createNotification(
                    SUBMISSION_HEADER,
                    NotificationType.REQUEST,
                    new String[]{"submissionId", "status"},
                    new Object[]{submissionToUpdate.getId(), submissionToUpdate.getStatus()}
            );
            pushNotificationService.sendNotification(user.getFcmToken(), notificationObject);

            //TODO implement ties into the hyperledger service here if the submission is successful
        }
        return submissionToUpdate;
    }

    private List<SubmissionDTO> submissionListToDTOList(List<Submission> submissionList) {
        List<SubmissionDTO> submissionDTOList = new ArrayList<>();
        submissionList.forEach((submission -> {
            SubmissionDTO dto = new SubmissionDTO();
            dto.setData(storageService.loadData(submission.getData()));
            dto.setId(submission.getId());
            dto.setUserId(submission.getUserId());
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
}
