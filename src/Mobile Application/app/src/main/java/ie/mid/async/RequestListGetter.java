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
import ie.mid.model.Profile;
import ie.mid.model.ViewableRequest;
import ie.mid.pojo.Party;
import ie.mid.pojo.Request;
import ie.mid.pojo.User;
import ie.mid.util.InternetUtil;

public class RequestListGetter extends AsyncTask<Void, Void, List<ViewableRequest>> {

    private Profile profile;
    private RequestListTaskCompleted callBack;
    private RequestService requestService;
    private UserService userService;
    private PartyService partyService;
    private WeakReference<Context> context;
    private HashMap<String,String> cachedUsers;

    public RequestListGetter(Context context, RequestListTaskCompleted callBack, Profile profile){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.requestService = new RequestService(context);
        this.userService = new UserService(context);
        this.partyService = new PartyService(context);
        this.profile = profile;
        this.cachedUsers = new HashMap<>();
    }

    @Override
    protected List<ViewableRequest> doInBackground(Void... voids) {
        List<Request> sent = new ArrayList<>();
        List<Request> received = new ArrayList<>();
        List<ViewableRequest> viewableRequestList = new ArrayList<>();
        if(InternetUtil.isServerLive(context.get())) {
            received.addAll(requestService.getRecipientRequests(profile.getServerId()));
            for(Request request: received){
                viewableRequestList.add(new ViewableRequest(request,getPartyUsername(request.getSender()))); //get the name of the person we got it from
            }
            sent.addAll(requestService.getSenderRequests(profile.getServerId()));
            for(Request request: sent){
                viewableRequestList.add(new ViewableRequest(request,getPartyUsername(request.getRecipient()))); //get the name of the person we sent it to
            }
            return viewableRequestList;
        }
        else
            return null;
    }

    @Override
    protected void onPostExecute(List<ViewableRequest> result) {


        callBack.onTaskComplete(result);
    }

    @Override
    protected void onPreExecute() {
    }

    private String getPartyUsername(String id){
        String name = cachedUsers.get(id);
        if(name == null) {
            User user = userService.getUser(id);
            if (user == null) {
                Party party = partyService.getParty(id);
                if (party != null){
                    cachedUsers.put(id,party.getName());
                    return party.getName();
                }
                return null;
            } else {
                cachedUsers.put(id,user.getNickname());
                return user.getNickname();
            }
        }
        return name;
    }

}
