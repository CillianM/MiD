package ie.mid.backend;

import android.content.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

import ie.mid.pojo.InformationRequest;
import ie.mid.pojo.Request;

public class RequestService {
    private BackendService backendService;
    private Context context;
    private ObjectMapper mapper;

    public RequestService(Context context) {
        this.context = context;
        this.mapper = new ObjectMapper();
        this.backendService = new BackendService(context, "/request");
    }

    public Request submitRequest(InformationRequest request) {
        backendService.setEndpointExtention("/request");
        String returnedSubmission = backendService.sendPost(request.toJsonString());
        if (returnedSubmission != null) {
            try {
                return mapper.readValue(returnedSubmission, Request.class);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public List<Request> getRecipientRequests(String userID) {
        backendService.setEndpointExtention("/request/recipient/" + userID);

        String returnedRequests = backendService.sendGet();
        if (returnedRequests != null) {
            try {
                return mapper.readValue(
                        returnedRequests,
                        mapper.getTypeFactory().constructParametricType(List.class, Request.class)
                );
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public List<Request> getSenderRequests(String userID) {
        backendService.setEndpointExtention("/request/sender/" + userID);

        String returnedRequests = backendService.sendGet();
        if (returnedRequests != null) {
            try {
                return mapper.readValue(
                        returnedRequests,
                        mapper.getTypeFactory().constructParametricType(List.class, Request.class)
                );
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public Request getRequest(String requestId) {
        backendService.setEndpointExtention("/request/" + requestId);

        String returnedRequest = backendService.sendGet();
        if (returnedRequest != null) {
            try {
                return mapper.readValue(returnedRequest, Request.class);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public Request updateRequest(String requestId, InformationRequest request) {
        backendService.setEndpointExtention("/request/" + requestId);
        String returnedSubmission = backendService.sendPut(request.toJsonString());
        if (returnedSubmission != null) {
            try {
                return mapper.readValue(returnedSubmission, Request.class);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}
