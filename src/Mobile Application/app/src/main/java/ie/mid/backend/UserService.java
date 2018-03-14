package ie.mid.backend;


import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import ie.mid.handler.DatabaseHandler;
import ie.mid.model.HttpCall;
import ie.mid.model.Profile;
import ie.mid.pojo.User;
import ie.mid.util.HashUtil;

import static android.content.ContentValues.TAG;

public class UserService {

    private BackendService backendService;
    private Context context;
    private ObjectMapper mapper;

    public UserService(Context context) {
        this.context = context;
        this.mapper = new ObjectMapper();
        this.backendService = new BackendService(context, "/user");
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
            profile.setPrivateKey(HashUtil.byteToBase64(keyPair.getPrivate().getEncoded()).replace("\n",""));
            profile.setPublicKey(HashUtil.byteToBase64(keyPair.getPublic().getEncoded()).replace("\n",""));


            DatabaseHandler handler = new DatabaseHandler(context);
            handler.open();
            String fcm = handler.returnFcmToken();
            //save to backend
            backendService.setEndpointExtention("/user");
            User user = new User();
            user.setNickname(profile.getName());
            user.setPublicKey(profile.getPublicKey());
            user.setFcmToken(fcm);
            String json = mapper.writeValueAsString(user);
            HttpCall httpCall = new HttpCall();
            httpCall.setJsonBody(json);
            String createdUser = backendService.sendPost(httpCall);
            if (createdUser != null) {
                user = mapper.readValue(createdUser, User.class);
                //save to local storage
                profile = handler.createProfile(profile.getName(), profile.getImageUrl(), profile.getHash(), profile.getSalt(),user.getId(),user.getUserToken(), profile.getPublicKey(), profile.getPrivateKey());
                handler.close();
                return profile;
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.toString());
        }
        return null;
    }

    public User getUser(String userId,HttpCall httpCall) {
        backendService.setEndpointExtention("/user/" + userId);

        String returnedParty = backendService.sendGet(httpCall);
        if (returnedParty != null) {
            try {
                return mapper.readValue(returnedParty, User.class);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }


}
