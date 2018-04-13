package ie.mid.backend;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import ie.mid.model.HttpCall;
import ie.mid.pojo.IdentityType;
import ie.mid.pojo.Key;

public class KeyService {
    private BackendService backendService;
    private Context context;
    private ObjectMapper mapper;

    public KeyService(Context context) {
        this.context = context;
        this.mapper = new ObjectMapper();
        this.backendService = new BackendService(context, "/key");
    }

    public Key getKey(String partyId) {
        this.backendService.setEndpointExtention("/key/" + partyId);
        String keyString = backendService.sendGet(new HttpCall());
        if (keyString != null) {
            try {
                return mapper.readValue(keyString,Key.class);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public IdentityType getIdentityType(String indentityTypeId) {
        this.backendService.setEndpointExtention("/identitytype/" + indentityTypeId);
        String identityType = backendService.sendGet(new HttpCall());
        if (identityType != null) {
            try {
                return mapper.readValue(identityType,IdentityType.class);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}
