package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ie.mid.backend.PartyService;
import ie.mid.backend.RequestService;
import ie.mid.backend.UserService;
import ie.mid.interfaces.RequestListTaskCompleted;
import ie.mid.model.HttpCall;
import ie.mid.model.Profile;
import ie.mid.model.ViewableRequest;
import ie.mid.pojo.Party;
import ie.mid.pojo.Request;
import ie.mid.pojo.User;
import ie.mid.util.EncryptionUtil;
import ie.mid.util.InternetUtil;

public class RequestListGetter extends AsyncTask<Void, Void, List<Request>> {

    private Profile profile;
    private RequestListTaskCompleted callBack;
    private RequestService requestService;

    private WeakReference<Context> context;

    public RequestListGetter(Context context, RequestListTaskCompleted callBack, Profile profile){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.requestService = new RequestService(context);
        this.profile = profile;
    }

    @Override
    protected List<Request> doInBackground(Void... voids) {
        List<Request> totalList = new ArrayList<>();
        if(InternetUtil.isServerLive(context.get())) {
            HttpCall httpCall = new HttpCall();
            String id = profile.getServerId();
            String password = EncryptionUtil.encryptText(id,profile.getPrivateKey());
            if(password != null) {
                httpCall.setAuthHeader(id,password);
                List<Request> recipientRequests = requestService.getRecipientRequests(profile.getServerId(),httpCall);
                List<Request> senderRequests = requestService.getSenderRequests(profile.getServerId(),httpCall);
                if(recipientRequests != null && senderRequests != null) {
                    totalList.addAll(recipientRequests);
                    totalList.addAll(senderRequests);
                    return totalList;
                }
                return null;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Request> result) {


        callBack.onTaskComplete(result);
    }

    @Override
    protected void onPreExecute() {
    }

}
