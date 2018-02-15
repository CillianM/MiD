package ie.mid.backend;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import ie.mid.pojo.IdentityType;

public class IdentityTypeService {

    private BackendService backendService;
    private Context context;
    private ObjectMapper mapper;

    public IdentityTypeService(Context context) {
        this.context = context;
        this.mapper = new ObjectMapper();
        this.backendService = new BackendService(context, "/identitytype");
    }

    public List<IdentityType> getIdentityTypes() {
        this.backendService.setEndpointExtention("/identitytype");
        String identityTypeListing = backendService.sendGet();
        if (identityTypeListing != null) {
            try {
                return mapper.readValue(
                        identityTypeListing,
                        mapper.getTypeFactory().constructParametricType(List.class, IdentityType.class)
                );
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public IdentityType getIdentityType(String indentityTypeId) {
        this.backendService.setEndpointExtention("/identitytype/" + indentityTypeId);
        String identityType = backendService.sendGet();
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
