package ie.mid.backend;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import ie.mid.pojo.Submission;

/**
 * Created by Cillian on 02/02/2018.
 */

public class SubmissionService {

    private BackendService backendService;
    private Context context;
    private ObjectMapper mapper;

    public SubmissionService(Context context) {
        this.context = context;
        this.mapper = new ObjectMapper();
        this.backendService = new BackendService(context, "/submission");
    }

    public Submission submitIdentity(Submission submission) {
        backendService.setEndpointExtention("/submission");
        String returnedSubmission = backendService.sendPost(submission.toJsonString());
        if (returnedSubmission != null) {
            try {
                return mapper.readValue(returnedSubmission, Submission.class);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public List<Submission> getSubmissions(String userID) {
        backendService.setEndpointExtention("/submission/user/" + userID);

        String returnedSubmissions = backendService.sendGet();
        if (returnedSubmissions != null) {
            try {
                return mapper.readValue(
                        returnedSubmissions,
                        mapper.getTypeFactory().constructParametricType(List.class, Submission.class)
                );
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public Submission getSubmission(String submissionId) {
        backendService.setEndpointExtention("/submission/" + submissionId);

        String returnedSubmissions = backendService.sendGet();
        if (returnedSubmissions != null) {
            try {
                return mapper.readValue(returnedSubmissions, Submission.class);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}
