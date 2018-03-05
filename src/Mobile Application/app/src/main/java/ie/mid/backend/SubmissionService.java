package ie.mid.backend;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import ie.mid.model.HttpCall;
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

    public Submission submitIdentity(HttpCall httpCall) {
        backendService.setEndpointExtention("/submission");
        String returnedSubmission = backendService.sendPost(httpCall);
        if (returnedSubmission != null) {
            try {
                return mapper.readValue(returnedSubmission, Submission.class);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public List<Submission> getSubmissions(String userID,HttpCall httpCall) {
        backendService.setEndpointExtention("/submission/user/" + userID);

        String returnedSubmissions = backendService.sendGet(httpCall);
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

    public Submission getSubmission(String submissionId,HttpCall httpCall) {
        backendService.setEndpointExtention("/submission/" + submissionId);

        String returnedSubmissions = backendService.sendGet(httpCall);
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
