package ie.mid.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import ie.mid.backend.IdentityTypeService;
import ie.mid.backend.SubmissionService;
import ie.mid.enums.CardStatus;
import ie.mid.interfaces.CardTaskCompleted;
import ie.mid.model.CardType;
import ie.mid.model.HttpCall;
import ie.mid.model.Profile;
import ie.mid.pojo.IdentityType;
import ie.mid.pojo.Submission;
import ie.mid.util.EncryptionUtil;
import ie.mid.util.InternetUtil;

public class CardGetter extends AsyncTask<Void, Void, List<CardType>> {

    private CardTaskCompleted callBack;
    private SubmissionService submissionService;
    private List<CardType> cardTypes;
    private List<IdentityType> identityTypes;
    private IdentityTypeService identityTypeService;
    private WeakReference<Context> context;
    private Profile profile;

    public CardGetter(Context context, CardTaskCompleted callBack, IdentityTypeService identityTypeService, SubmissionService submissionService,Profile profile, List<CardType> cardTypes){
        this.context = new WeakReference<>(context);
        this.callBack = callBack;
        this.identityTypeService = identityTypeService;
        this.submissionService = submissionService;
        this.cardTypes = cardTypes;
        this.profile = profile;
    }

    @Override
    protected List<CardType> doInBackground(Void... voids) {
        if(InternetUtil.isServerLive(context.get())) {

            HttpCall httpCall = new HttpCall();
            String id = profile.getServerId();
            String password = EncryptionUtil.encryptText(id,profile.getPrivateKey());
            if(password != null) {
                httpCall.setAuthHeader(id,password);
                identityTypes = identityTypeService.getIdentityTypes();
                if (identityTypes != null) {
                    for (CardType cardType : cardTypes) {
                        if (cardType.getSubmissionId() != null && !Objects.equals(cardType.getSubmissionId(), CardStatus.NOT_VERIFIED.toString())) {
                            Submission submission = submissionService.getSubmission(cardType.getSubmissionId(),httpCall);
                            if (submission != null) {
                                cardType.setStatus(submission.getStatus());
                            }
                            IdentityType identityType = getIdentityById(cardType.getCardId());
                            if (identityType == null) {
                                cardType.setStatus(CardStatus.DELETED.toString());
                            }
                        }
                    }
                    return cardTypes;
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<CardType> result) {
        callBack.onTaskComplete(result);
    }

    @Override
    protected void onPreExecute() {
    }

    private IdentityType getIdentityById(String id){
        for(IdentityType identityType: identityTypes){
            if(identityType.getId().equals(id))
                return identityType;
        }
        return null;
    }

}
