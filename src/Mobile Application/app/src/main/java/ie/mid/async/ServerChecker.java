package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import ie.mid.interfaces.IsServerLiveCheckCompleted;
import ie.mid.util.InternetUtil;

public class ServerChecker extends AsyncTask<Void, Void, Boolean> {

    private IsServerLiveCheckCompleted callBack;
    private WeakReference<Context> context;

    public ServerChecker(Context context, IsServerLiveCheckCompleted callBack) {
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return InternetUtil.isServerLive(context.get());
    }

    @Override
    protected void onPostExecute(Boolean result) {
        callBack.onTaskComplete(result);
    }

    @Override
    protected void onPreExecute() {
    }
}
