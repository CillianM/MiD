package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import ie.mid.backend.KeyService;
import ie.mid.backend.PartyService;
import ie.mid.interfaces.KeyTaskCompleted;
import ie.mid.pojo.Key;
import ie.mid.pojo.Party;
import ie.mid.util.InternetUtil;

/**
 * Created by Cillian on 13/04/2018.
 */

public class KeyGetter extends AsyncTask<String, Void, Key> {

    private KeyTaskCompleted callBack;
    private KeyService keyService;
    private PartyService partyService;
    private WeakReference<Context> context;

    public KeyGetter(Context context,KeyTaskCompleted callBack){
        this.context = new WeakReference<>(context);;
        this.callBack = callBack;
        this.keyService = new KeyService(context);
        this.partyService = new PartyService(context);

    }

    @Override
    protected Key doInBackground(String... partyId) {
        if(InternetUtil.isServerLive(context.get())) {
            Party party = partyService.getParty(partyId[0]);
            if (party != null)
                return keyService.getKey(party.getId());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Key result) {
        callBack.onTaskComplete(result);
    }

    @Override
    protected void onPreExecute() {
    }

}
