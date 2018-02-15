package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import ie.mid.backend.IdentityTypeService;
import ie.mid.backend.PartyService;
import ie.mid.backend.RequestService;
import ie.mid.backend.UserService;
import ie.mid.interfaces.RequestTaskCompleted;
import ie.mid.model.Profile;
import ie.mid.model.ViewableRequest;
import ie.mid.pojo.IdentityType;
import ie.mid.pojo.Party;
import ie.mid.pojo.Request;
import ie.mid.pojo.User;
import ie.mid.util.InternetUtil;


public class RequestGetter extends AsyncTask<Void, Void, ViewableRequest> {

    private Profile profile;
    private RequestTaskCompleted callBack;
    private RequestService requestService;
    private UserService userService;
    private PartyService partyService;
    private IdentityTypeService identityTypeService;
    private WeakReference<Context> context;
    private String id;
    private IdentityType identityType;

    public RequestGetter(Context context, RequestTaskCompleted callBack, Profile profile,String id){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.requestService = new RequestService(context);
        this.userService = new UserService(context);
        this.partyService = new PartyService(context);
        this.identityTypeService = new IdentityTypeService(context);
        this.profile = profile;
        this.id = id;
        this.identityType = null;
    }

    @Override
    protected ViewableRequest doInBackground(Void... voids) {
        if(InternetUtil.isServerLive(context.get())) {
            Request request = requestService.getRequest(id);
            if(request != null) {
                identityType = identityTypeService.getIdentityType(request.getIndentityTypeId());
                if (request.getSender().equals(profile.getServerId()))
                    return new ViewableRequest(request,getPartyUsername(request.getRecipient()));
                else
                    return new ViewableRequest(request,getPartyUsername(request.getSender()));
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(ViewableRequest result) {

        callBack.onTaskComplete(identityType,result);
    }

    @Override
    protected void onPreExecute() {
    }

    private String getPartyUsername(String id){
        User user = userService.getUser(id);
        if (user == null) {
            Party party = partyService.getParty(id);
            if (party != null){
                return party.getName();
            }
            return null;
        } else {
            return user.getNickname();
        }
    }

}
