package ie.mid.backend;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import ie.mid.model.HttpCall;
import ie.mid.pojo.Certificate;

public class CertificateService {

    private BackendService backendService;
    private Context context;
    private ObjectMapper mapper;

    public CertificateService(Context context) {
        this.context = context;
        this.mapper = new ObjectMapper();
        this.backendService = new BackendService(context, "/certificate");
    }

    public Certificate getCertificate(HttpCall call, String certId) {
        this.backendService.setEndpointExtention("/certificate/" + certId);
        String identityType = backendService.sendGet(call);
        if (identityType != null) {
            try {
                return mapper.readValue(identityType,Certificate.class);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}
