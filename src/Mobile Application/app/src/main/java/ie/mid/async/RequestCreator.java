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

/**
 * Created by Cillian on 13/02/2018.
 */

public class RequestCreator extends AsyncTask<InformationRequest, Void, Request> {

    private RequestCreateTaskCompleted callBack;
    private RequestService requestService;
    private WeakReference<Context> context;
    private Profile profile;

    public RequestCreator(Context context, RequestCreateTaskCompleted callBack,Profile profile){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.requestService = new RequestService(context);
        this.profile = profile;
    }

    @Override
    protected Request doInBackground(InformationRequest... requests) {
        if(InternetUtil.isServerLive(context.get())) {
            HttpCall httpCall = new HttpCall();
            httpCall.setJsonBody(requests[0].toJsonString());
            String id = profile.getServerId();
            String password = EncryptionUtil.encryptText(profile.getServerToken(),profile.getPrivateKey());
            if(password != null) {
                httpCall.setAuthHeader(id,password);
                return requestService.submitRequest(httpCall);
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
