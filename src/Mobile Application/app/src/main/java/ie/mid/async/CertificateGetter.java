package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import ie.mid.backend.CertificateService;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.CertificateTaskCompleted;
import ie.mid.model.HttpCall;
import ie.mid.model.Profile;
import ie.mid.pojo.Certificate;
import ie.mid.util.EncryptionUtil;
import ie.mid.util.InternetUtil;

public class CertificateGetter extends AsyncTask<Void, Void, Certificate> {

    private CertificateTaskCompleted callBack;
    private CertificateService certificateService;
    private WeakReference<Context> context;
    private String certId;
    private Profile profile;

    public CertificateGetter(Context context,CertificateTaskCompleted callBack,Profile profile, String certId){
        this.certId = certId;
        this.context = new WeakReference<>(context);;
        this.callBack = callBack;
        this.certificateService = new CertificateService(context);
        this.profile = profile;
    }

    @Override
    protected Certificate doInBackground(Void... voids) {
        if(InternetUtil.isServerLive(context.get())) {
            HttpCall httpCall = new HttpCall();
            String id = profile.getServerId();
            String password = EncryptionUtil.encryptText(id,profile.getPrivateKey());
            if(password != null) {
                httpCall.setAuthHeader(id,password);
                return certificateService.getCertificate(httpCall, certId);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Certificate result) {
        callBack.onTaskComplete(result);
    }

    @Override
    protected void onPreExecute() {
    }
}
