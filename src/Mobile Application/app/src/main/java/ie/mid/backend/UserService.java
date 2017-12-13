package ie.mid.backend;


import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import ie.mid.handler.DatabaseHandler;
import ie.mid.model.Profile;
import ie.mid.pojo.User;

public class UserService {

    private BackendService backendService;
    private Context context;
    private ObjectMapper mapper;

    public UserService(Context context) {
        this.context = context;
        this.mapper = new ObjectMapper();
        this.backendService = new BackendService("/user");
    }

    public void updateFcm(String fcm) {
        DatabaseHandler handler = new DatabaseHandler(context);
        handler.open();
        if(handler.returnFcmCount() == 0){
            handler.createFcm(fcm);
        }
        else{
            handler.updateFcm(fcm);
        }
        handler.close();
    }

    public Profile createUser(Profile profile) {
        //generate keypair
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            profile.setPrivateKey(new BigInteger( keyPair.getPrivate().getEncoded()).toString(16));
            profile.setPublicKey(new BigInteger(keyPair.getPublic().getEncoded()).toString(16));


            DatabaseHandler handler = new DatabaseHandler(context);
            handler.open();
            String fcm = handler.returnFcmToken();
            //save to backend
            backendService.setEndpointExtention("/user");
            User user = new User();
            user.setPublicKey(profile.getPublicKey());
            user.setFcmToken(fcm);
            String json = mapper.writeValueAsString(user);
            String createdUser = backendService.sendPost(json);
            user = mapper.readValue(createdUser, User.class);

            //save to local storage
            return handler.createProfile(profile.getName(), profile.getImageUrl(), profile.getHash(), profile.getSalt(),user.getId(), profile.getPublicKey(),profile.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
