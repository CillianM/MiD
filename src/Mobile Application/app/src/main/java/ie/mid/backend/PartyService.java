package ie.mid.backend;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import ie.mid.pojo.Party;

/**
 * Created by Cillian on 13/02/2018.
 */

public class PartyService {

    private BackendService backendService;
    private Context context;
    private ObjectMapper mapper;

    public PartyService(Context context) {
        this.context = context;
        this.mapper = new ObjectMapper();
        this.backendService = new BackendService(context, "/party");
    }

    public Party getParty(String partyId) {
        backendService.setEndpointExtention("/party/" + partyId);

        String returnedParty = backendService.sendGet();
        if (returnedParty != null) {
            try {
                return mapper.readValue(returnedParty, Party.class);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}
