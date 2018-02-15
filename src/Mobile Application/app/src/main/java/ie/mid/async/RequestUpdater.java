package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import ie.mid.backend.RequestService;
import ie.mid.interfaces.RequestCreateTaskCompleted;
import ie.mid.pojo.InformationRequest;
import ie.mid.pojo.Request;
import ie.mid.util.InternetUtil;

public class RequestUpdater extends AsyncTask<InformationRequest, Void, Request> {

    private RequestCreateTaskCompleted callBack;
    private RequestService requestService;
    private WeakReference<Context> context;
    private String requestId;

    public RequestUpdater(Context context, RequestCreateTaskCompleted callBack,String requestId){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.requestService = new RequestService(context);
        this.requestId = requestId;
    }

    @Override
    protected Request doInBackground(InformationRequest... requests) {
        if(InternetUtil.isServerLive(context.get()))
            return requestService.updateRequest(requestId, requests[0]);
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
