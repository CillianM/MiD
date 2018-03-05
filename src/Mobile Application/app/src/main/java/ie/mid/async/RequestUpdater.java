package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import ie.mid.backend.RequestService;
import ie.mid.interfaces.RequestCreateTaskCompleted;
import ie.mid.model.HttpCall;
import ie.mid.model.Profile;
import ie.mid.pojo.InformationRequest;
import ie.mid.pojo.Request;
import ie.mid.util.EncryptionUtil;
import ie.mid.util.InternetUtil;

public class RequestUpdater extends AsyncTask<InformationRequest, Void, Request> {

    private RequestCreateTaskCompleted callBack;
    private RequestService requestService;
    private WeakReference<Context> context;
    private String requestId;
    private Profile profile;

    public RequestUpdater(Context context, RequestCreateTaskCompleted callBack, Profile profile, String requestId){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.requestService = new RequestService(context);
        this.requestId = requestId;
        this.profile = profile;
    }

    @Override
    protected Request doInBackground(InformationRequest... requests) {
        if(InternetUtil.isServerLive(context.get())) {
            HttpCall httpCall = new HttpCall();
            httpCall.setJsonBody(requests[0].toJsonString());
            String id = profile.getServerId();
            String password = EncryptionUtil.encryptText(id,profile.getPrivateKey());
            if(password != null) {
                httpCall.setAuthHeader(id,password);
                return requestService.updateRequest(requestId,httpCall);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Request result) {
        callBack.onTaskComplete(result);
    }

    @Override
    protected void onPreExecute() {
    }

}
