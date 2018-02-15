package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import ie.mid.backend.IdentityTypeService;
import ie.mid.interfaces.IdentityTaskCompleted;
import ie.mid.pojo.IdentityType;
import ie.mid.util.InternetUtil;

/**
 * Created by Cillian on 13/02/2018.
 */

public class IdentityTypeGetter extends AsyncTask<Void, Void, List<IdentityType>> {

    private IdentityTaskCompleted callBack;
    private IdentityTypeService identityTypeService;
    private WeakReference<Context> context;

    public IdentityTypeGetter(Context context,IdentityTaskCompleted callBack){
        this.context = new WeakReference<>(context);;
        this.callBack = callBack;
        this.identityTypeService = new IdentityTypeService(context);
    }

    @Override
    protected List<IdentityType> doInBackground(Void... voids) {
        if(InternetUtil.isServerLive(context.get()))
            return identityTypeService.getIdentityTypes();
        return null;
    }

    @Override
    protected void onPostExecute(List<IdentityType> result) {
        callBack.onTaskComplete(result);
    }

    @Override
    protected void onPreExecute() {
    }

}
