package ie.mid.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

import ie.mid.pojo.Submission;

/**
 * Created by Cillian on 13/02/2018.
 */

public class ViewableSubmission extends Submission{

    private String partyName;


    public ViewableSubmission(Submission submission, String partyName) {
        this.setId(submission.getId());
        this.setData(submission.getData());
        this.setDate(submission.getDate());
        this.setPartyId(submission.getPartyId());
        this.setStatus(submission.getStatus());
        this.setUserId(submission.getUserId());
        this.partyName = partyName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
}
