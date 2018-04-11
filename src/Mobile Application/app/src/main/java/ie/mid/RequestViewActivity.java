package ie.mid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ie.mid.adapter.CardFieldListAdapter;
import ie.mid.adapter.IdentityRequestAdapter;
import ie.mid.async.CertificateGetter;
import ie.mid.async.RequestGetter;
import ie.mid.async.RequestUpdater;
import ie.mid.async.SubmissionGetter;
import ie.mid.enums.CardStatus;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.CertificateTaskCompleted;
import ie.mid.interfaces.RequestCreateTaskCompleted;
import ie.mid.interfaces.RequestTaskCompleted;
import ie.mid.interfaces.SubmissionTaskCompleted;
import ie.mid.model.CardField;
import ie.mid.model.CardType;
import ie.mid.model.Field;
import ie.mid.model.Profile;
import ie.mid.model.ViewableRequest;
import ie.mid.pojo.Certificate;
import ie.mid.pojo.IdentityType;
import ie.mid.pojo.InformationRequest;
import ie.mid.pojo.Request;
import ie.mid.pojo.Submission;
import ie.mid.util.InternetUtil;

public class RequestViewActivity extends AppCompatActivity implements RequestTaskCompleted, RequestCreateTaskCompleted,SubmissionTaskCompleted,CertificateTaskCompleted {

    Profile profile;
    String requestId;
    IdentityType identityType;
    Request viewableRequest;
    IdentityRequestAdapter identityRequestAdapter;
    Button acceptButton;
    Button rejectButton;
    RelativeLayout requestLayout;
    List<Field> selectedFields;
    String userId;
    InformationRequest informationRequest;
    private String submissionId;
    private Certificate retrievedCertificate;
    boolean fromCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_view);
        getSupportActionBar().setTitle("Request");
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        userId = getIntent().getStringExtra("userId");
        if(userId != null) {
            profile = handler.getProfile(userId);
        } else {
            profile = handler.getProfileByServerId(getIntent().getStringExtra("serverId"));
        }
        profile = handler.getProfile(userId);
        requestId = getIntent().getStringExtra("requestId");
        fromCreate = getIntent().getBooleanExtra("fromCreate",false);
        handler.close();
        requestLayout = findViewById(R.id.request_layout);
        acceptButton = findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean containsTrue = identityRequestAdapter.getBooleanList().contains(true);
                if (!containsTrue) {
                    Toast.makeText(getApplicationContext(), "No item selected", Toast.LENGTH_SHORT).show();
                } else {
                    acceptRequest();
                }
            }
        });
        rejectButton = findViewById(R.id.reject_button);
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectRequest();
            }
        });
        acceptButton.setVisibility(View.INVISIBLE);
        rejectButton.setVisibility(View.INVISIBLE);


        showLoading();
        if (InternetUtil.isNetworkAvailable(getApplicationContext())) {
            new RequestGetter(getApplicationContext(), this, profile, requestId).execute();
        } else {
            networkError();
        }

    }

    @Override
    public void onBackPressed(){
        if(fromCreate) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("userId", profile.getId());
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private void submitRequest(InformationRequest request) {
        informationRequest = request;
        showLoading();
        if(InternetUtil.isNetworkAvailable(getApplicationContext()))
            new SubmissionGetter(getApplicationContext(),this,profile,submissionId).execute();
        else
            networkError();

    }

    private void rejectRequest() {
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        CardType cardType = handler.getUserCardByIdentityType(userId, viewableRequest.getIndentityTypeId());
        submissionId = cardType.getSubmissionId();
        handler.close();
        InformationRequest informationRequest = new InformationRequest();
        informationRequest.setRecipientId(viewableRequest.getRecipientId());
        informationRequest.setSenderId(viewableRequest.getSenderId());
        informationRequest.setIdentityTypeFields(viewableRequest.getIdentityTypeFields());
        informationRequest.setStatus(CardStatus.REJECTED.toString());
        informationRequest.setIndentityTypeId(viewableRequest.getIndentityTypeId());
        submitRequest(informationRequest);
    }

    private void acceptRequest() {
        if (hasValidId()) {
            InformationRequest informationRequest = new InformationRequest();
            informationRequest.setRecipientId(viewableRequest.getRecipientId());
            informationRequest.setSenderId(viewableRequest.getSenderId());
            informationRequest.setIdentityTypeFields(getIdentityTypeFields());
            informationRequest.setIdentityTypeValues(getIdentityTypeValues());
            informationRequest.setIndentityTypeId(viewableRequest.getIndentityTypeId());
            informationRequest.setStatus(CardStatus.ACCEPTED.toString());
            submitRequest(informationRequest);
        } else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(RequestViewActivity.this, R.style.AlertDialog);
            builder.setMessage("Identity type is not currently validated").setPositiveButton("Ok", dialogClickListener).show();
        }
    }

    private boolean hasId() {
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        CardType cardType = handler.getUserCardByIdentityType(userId, viewableRequest.getIndentityTypeId());
        handler.close();
        return cardType != null;
    }

    private boolean hasValidId() {
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        CardType cardType = handler.getUserCardByIdentityType(userId, viewableRequest.getIndentityTypeId());
        handler.close();
        return cardType.getStatus().toUpperCase().equals(CardStatus.ACCEPTED.toString());
    }

    private String getIdentityTypeFields() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < selectedFields.size(); i++) {
            builder.append(selectedFields.get(i).getType()).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public String getIdentityTypeValues() {
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        CardType cardType = handler.getUserCardByIdentityType(userId, viewableRequest.getIndentityTypeId());
        submissionId = cardType.getSubmissionId();
        handler.close();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < selectedFields.size(); i++) {
            if (identityRequestAdapter.getBooleanList().get(i)) {
                for (CardField field : cardType.getDataList()) {
                    if (field.getFieldType().equals(selectedFields.get(i).getType())) {
                        builder.append(field.getFieldEntry()).append(",");
                    }
                }
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }


    private void networkError() {
        findViewById(R.id.request_progress).setVisibility(View.GONE);
        findViewById(R.id.request_info).setVisibility(View.VISIBLE);
        TextView info = findViewById(R.id.request_info);
        info.setText("No network connection");
    }

    private void showLoading() {
        requestLayout.setVisibility(View.GONE);
        findViewById(R.id.request_progress).setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        requestLayout.setVisibility(View.VISIBLE);
        findViewById(R.id.request_progress).setVisibility(View.GONE);

    }

    private void showCertLoading() {
        acceptButton.setVisibility(View.GONE);
        findViewById(R.id.cert_progress).setVisibility(View.VISIBLE);
    }

    private void hideCertLoading() {
        acceptButton.setVisibility(View.VISIBLE);
        findViewById(R.id.cert_progress).setVisibility(View.GONE);

    }

    @Override
    public void onTaskComplete(IdentityType identityType, Request viewableRequest) {
        if (viewableRequest != null && identityType != null) {
            String status = viewableRequest.getStatus();
            if (status.equals(CardStatus.ACCEPTED.toString()) || status.equals(CardStatus.PENDING.toString()) || status.equals(CardStatus.SUBMITTED.toString())) {
                this.viewableRequest = viewableRequest;
                this.identityType = identityType;
                if (viewableRequest.getRecipientId().equals(profile.getServerId())) {
                    setupRecipientView();
                } else {
                    setupSenderView();
                }
                hideLoading();
            } else {
                requestLayout.setVisibility(View.GONE);
                findViewById(R.id.request_progress).setVisibility(View.GONE);
                findViewById(R.id.request_info).setVisibility(View.VISIBLE);
                TextView info = findViewById(R.id.request_info);
                String message = "Request is " + status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
                info.setText(message);
            }
        } else {
            requestLayout.setVisibility(View.GONE);
            findViewById(R.id.request_progress).setVisibility(View.GONE);
            findViewById(R.id.request_info).setVisibility(View.VISIBLE);
            TextView info = findViewById(R.id.request_info);
            info.setText("Error retrieving data");
        }

    }

    private void setupRecipientView() {
        acceptButton.setVisibility(View.VISIBLE);
        rejectButton.setVisibility(View.VISIBLE);
        String status = viewableRequest.getStatus();
        if (!hasId() && !status.equals(CardStatus.ACCEPTED.toString())) {
            sendToCreate();
        } else {
            TextView identityTypeTitle = findViewById(R.id.identity_type_title);
            String message = "Request from " + viewableRequest.getSenderName();
            getSupportActionBar().setTitle(message);
            String identityTypeText = viewableRequest.getSenderName() + " requests these fields from your \n" +  identityType.getName()
                    + "\nWhat fields would you like to send back?";
            identityTypeTitle.setText(identityTypeText);
            ListView requestedInfo = findViewById(R.id.identity_type_values);
            if (status.equals(CardStatus.PENDING.toString()) || status.equals(CardStatus.SUBMITTED.toString())) {
                selectedFields = new ArrayList<>();
                for (Field field : identityType.getFields()) {
                    if (viewableRequest.getIdentityTypeFields().contains(field.getType())) {
                        selectedFields.add(field);
                    }
                }

                identityRequestAdapter = new IdentityRequestAdapter(getApplicationContext(), selectedFields);
                requestedInfo.setAdapter(identityRequestAdapter);
            } else if (status.equals(CardStatus.ACCEPTED.toString())) {
                identityTypeText = viewableRequest.getSenderName() + " requested these fields from your \n" +  identityType.getName();
                identityTypeTitle.setText(identityTypeText);
                acceptButton.setVisibility(View.INVISIBLE);
                rejectButton.setText("Delete");
                String[] answers = viewableRequest.getIdentityTypeValues().split(",");
                String[] types = viewableRequest.getIdentityTypeFields().split(",");
                String[] titles = getIdentityTitles(identityType, types);
                List<CardField> cardFields = new ArrayList<>();
                for (int i = 0; i < answers.length; i++) {
                    CardField cardField = new CardField(answers[i], types[i]);
                    cardField.setFieldTitle(titles[i]);
                    cardFields.add(cardField);
                }
                requestedInfo.setAdapter(new CardFieldListAdapter(getApplicationContext(), cardFields));
            } else {
                selectedFields = new ArrayList<>();
                for (Field field : identityType.getFields()) {
                    if (viewableRequest.getIdentityTypeFields().contains(field.getType())) {
                        selectedFields.add(field);
                    }
                }

                identityRequestAdapter = new IdentityRequestAdapter(getApplicationContext(), selectedFields);
                requestedInfo.setAdapter(identityRequestAdapter);
            }
        }
    }

    private void setupSenderView() {
        String status = viewableRequest.getStatus();
        TextView identityTypeTitle = findViewById(R.id.identity_type_title);
        String message = "Request to " + viewableRequest.getRecipientName();
        getSupportActionBar().setTitle(message);
        String identityTypeText = "You requested these fields from their \n" +identityType.getName();
        identityTypeTitle.setText(identityTypeText);
        ListView requestedInfo = findViewById(R.id.identity_type_values);
        if (status.equals(CardStatus.PENDING.toString()) || status.equals(CardStatus.SUBMITTED.toString())) {
            identityTypeText = "You are awaiting these fields from their " +identityType.getName();
            identityTypeTitle.setText(identityTypeText);
            acceptButton.setVisibility(View.INVISIBLE);
            rejectButton.setText("Delete");
            String[] types = viewableRequest.getIdentityTypeFields().split(",");
            String[] titles = getIdentityTitles(identityType, types);
            List<CardField> cardFields = new ArrayList<>();
            for (int i = 0; i < types.length; i++) {
                CardField cardField = new CardField("", types[i]);
                cardField.setFieldTitle(titles[i]);
                cardFields.add(cardField);
            }
            requestedInfo.setAdapter(new CardFieldListAdapter(getApplicationContext(), cardFields));
        } else if (status.equals(CardStatus.ACCEPTED.toString())) {
            acceptButton.setVisibility(View.INVISIBLE);
            rejectButton.setText("Delete");
            String[] answers = viewableRequest.getIdentityTypeValues().split(",");
            String[] types = viewableRequest.getIdentityTypeFields().split(",");
            String[] titles = getIdentityTitles(identityType, types);
            List<CardField> cardFields = new ArrayList<>();
            for (int i = 0; i < answers.length; i++) {
                CardField cardField = new CardField(answers[i], types[i]);
                cardField.setFieldTitle(titles[i]);
                cardFields.add(cardField);
            }
            requestedInfo.setAdapter(new CardFieldListAdapter(getApplicationContext(), cardFields));
            getCertificate(viewableRequest.getCertificateId());
        } else {
            selectedFields = new ArrayList<>();
            for (Field field : identityType.getFields()) {
                if (viewableRequest.getIdentityTypeFields().contains(field.getType())) {
                    selectedFields.add(field);
                }
            }

            identityRequestAdapter = new IdentityRequestAdapter(getApplicationContext(), selectedFields);
            requestedInfo.setAdapter(identityRequestAdapter);
        }
    }

    private void sendToCreate() {
        Intent intent = new Intent(getApplicationContext(), CardCreateActivity.class);
        intent.putExtra("isRequest", true);
        intent.putExtra("card", identityType);
        intent.putExtra("userId", userId);
        Toast.makeText(getApplicationContext(), "Please create and submit this identity before answering the request", Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }

    private String[] getIdentityTitles(IdentityType identityType, String[] types) {
        String[] title = new String[types.length];
        for (int i = 0; i < title.length; i++) {
            title[i] = getIdentityTitle(identityType, types[i]);
        }
        return title;
    }

    private String getIdentityTitle(IdentityType identityType, String type) {
        for (Field field : identityType.getFields()) {
            if (field.getType().equals(type)) {
                return field.getName();
            }
        }
        return "";
    }

    public void finishActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("tab", 1);
        startActivity(intent);
        finish();
    }


    @Override
    public void onTaskComplete(Request request) {
        hideLoading();
        if (request != null) {
            finishActivity();

        } else {
            findViewById(R.id.request_progress).setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Error submitting request", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTaskComplete(Submission submission) {
        if(submission != null) {
            informationRequest.setCertificateId(submission.getCertId());
            if (InternetUtil.isNetworkAvailable(getApplicationContext())) {
                new RequestUpdater(getApplicationContext(), this, profile,viewableRequest.getId()).execute(informationRequest);
            } else {
                networkError();
            }
        } else{
            findViewById(R.id.request_progress).setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Error submitting request", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCertificate(String id){
        showCertLoading();
        if (InternetUtil.isNetworkAvailable(getApplicationContext())) {
            new CertificateGetter(getApplicationContext(), this, profile,id).execute();
        } else {
            networkError();
        }
    }

    @Override
    public void onTaskComplete(final Certificate certificate) {
        hideCertLoading();
        if(certificate != null) {
            retrievedCertificate = certificate;
            acceptButton.setVisibility(View.VISIBLE);
            acceptButton.setText("View Certificate");
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openCertificate(retrievedCertificate);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(),"Error retrieving certificate",Toast.LENGTH_SHORT).show();
        }
    }

    private void openCertificate(final Certificate certificate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.dialog_certificate, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                TextView dateText = dialogLayout.findViewById(R.id.date_text);
                TextView ownerText = dialogLayout.findViewById(R.id.owner_text);
                TextView trusteeText = dialogLayout.findViewById(R.id.trustee_text);
                String dateString = "Date Certified: " + certificate.getCreatedAt();
                String ownerString = "Certificate Owner: " + certificate.getOwnerName();
                String trusteeString = "Certified By: " + certificate.getCreatorName();
                dateText.setText(dateString);
                ownerText.setText(ownerString);
                trusteeText.setText(trusteeString);
            }
        });

        dialog.show();
    }
}
