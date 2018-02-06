package ie.mid.backend;

import android.content.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
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

    public Request submitRequest(Request request) {
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

    public List<Request> getRequests(String userID) {
        backendService.setEndpointExtention("/request/recipient/" + userID);

        String returnedSubmissions = backendService.sendGet();
        if (returnedSubmissions != null) {
            try {
                return mapper.readValue(
                        returnedSubmissions,
                        mapper.getTypeFactory().constructParametricType(List.class, Request.class)
                );
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}
