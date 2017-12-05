package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.SubmissionDTO;
import ie.mid.identityengine.enums.EntityStatus;
import ie.mid.identityengine.model.Submission;
import ie.mid.identityengine.repository.SubmissionRepository;
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

    @GetMapping(value = "/party/{partyId}")
    @ResponseBody
    public List<SubmissionDTO> getPartySubmissions(@PathVariable String partyId) {
        List<Submission> submissions = submissionRepository.findByPartyId(partyId);
        return submissionListToDTOList(submissions);
    }

    @GetMapping(value = "/user/{userId}")
    @ResponseBody
    public List<SubmissionDTO> getUserSubmissions(@PathVariable String userId) {
        List<Submission> submissions = submissionRepository.findByUserId(userId);
        return submissionListToDTOList(submissions);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public SubmissionDTO getSubmission(@PathVariable String id) {
        Submission submission = submissionRepository.findById(id);
        SubmissionDTO dto = new SubmissionDTO();
        dto.setData(submission.getData());
        dto.setId(submission.getId());
        dto.setUserId(submission.getUserId());
        dto.setPartyId(submission.getPartyId());
        dto.setStatus(submission.getStatus());
        return dto;
    }

    private List<SubmissionDTO> submissionListToDTOList(List<Submission> submissionList) {
        List<SubmissionDTO> submissionDTOList = new ArrayList<>();
        submissionList.forEach((submission -> {
            SubmissionDTO dto = new SubmissionDTO();
            dto.setData(submission.getData());
            dto.setId(submission.getId());
            dto.setUserId(submission.getUserId());
            dto.setPartyId(submission.getPartyId());
            dto.setStatus(submission.getStatus());
            submissionDTOList.add(dto);
        }));
        return submissionDTOList;
    }

    @PostMapping
    @ResponseBody
    public SubmissionDTO createSubmission(@RequestBody SubmissionDTO submissionToCreate) {
        Submission submission = new Submission();
        submission.setData(submissionToCreate.getData());
        submission.setStatus(EntityStatus.ACTIVE.toString());
        submission.setUserId(submissionToCreate.getUserId());
        submission.setPartyId(submissionToCreate.getPartyId());
        submission = submissionRepository.save(submission);
        submissionToCreate.setId(submission.getId());
        return submissionToCreate;
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public SubmissionDTO updateSubmission(@PathVariable String id, @RequestBody SubmissionDTO submissionToUpdate) {
        Submission submission = submissionRepository.findById(id);
        submission.setStatus(submissionToUpdate.getStatus());
        submissionRepository.save(submission);

        //TODO implement ties into the hyperledger service here if the submission is successful
        return submissionToUpdate;
    }
}
