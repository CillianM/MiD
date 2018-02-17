package ie.mid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.common.internal.Sets;

import java.util.ArrayList;
import java.util.List;

import ie.mid.adapter.CardFieldListAdapter;
import ie.mid.adapter.IdentityRequestAdapter;
import ie.mid.async.RequestGetter;
import ie.mid.async.RequestUpdater;
import ie.mid.enums.CardStatus;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.RequestCreateTaskCompleted;
import ie.mid.interfaces.RequestTaskCompleted;
import ie.mid.model.CardField;
import ie.mid.model.CardType;
import ie.mid.model.Field;
import ie.mid.model.Profile;
import ie.mid.model.ViewableRequest;
import ie.mid.pojo.IdentityType;
import ie.mid.pojo.InformationRequest;
import ie.mid.pojo.Request;
import ie.mid.util.InternetUtil;

public class RequestViewActivity extends AppCompatActivity implements RequestTaskCompleted, RequestCreateTaskCompleted {

    Profile profile;
    String requestId;
    IdentityType identityType;
    ViewableRequest viewableRequest;
    IdentityRequestAdapter identityRequestAdapter;
    boolean isRequest;
    Button acceptButton;
    Button rejectButton;
    List<Field> selectedFields;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_view);
        userId = getIntent().getStringExtra("userId");
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        profile = handler.getProfile(userId);
        requestId = getIntent().getStringExtra("requestId");
        isRequest = getIntent().getBooleanExtra("isRequest",true);
        handler.close();
        acceptButton = findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean containsTrue = identityRequestAdapter.getBooleanList().contains(true);
                if(!containsTrue){
                    Toast.makeText(getApplicationContext(),"No item selected",Toast.LENGTH_SHORT).show();
                }else {
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
        if(!isRequest){
            acceptButton.setVisibility(View.INVISIBLE);
            rejectButton.setVisibility(View.INVISIBLE);
        }

        showLoading();
        if(InternetUtil.isNetworkAvailable(getApplicationContext())){
            new RequestGetter(getApplicationContext(),this,profile,requestId).execute();
        } else{
            networkError();
        }

    }

    private void submitRequest(InformationRequest request){
        showLoading();
        if(InternetUtil.isNetworkAvailable(getApplicationContext())){
            new RequestUpdater(getApplicationContext(),this,viewableRequest.getId()).execute(request);
        } else{
            networkError();
        }

    }

    private void rejectRequest() {
        InformationRequest informationRequest = new InformationRequest();
        informationRequest.setRecipientId(viewableRequest.getRecipient());
        informationRequest.setSenderId(viewableRequest.getSender());
        informationRequest.setIdentityTypeFields(viewableRequest.getIdentityTypeFields());
        informationRequest.setIdentityTypeValues(CardStatus.REJECTED.toString());
        informationRequest.setIndentityTypeId(viewableRequest.getIndentityTypeId());
        submitRequest(informationRequest);
    }

    private void acceptRequest() {
        if(hasValidId()) {
            InformationRequest informationRequest = new InformationRequest();
            informationRequest.setRecipientId(viewableRequest.getRecipient());
            informationRequest.setSenderId(viewableRequest.getSender());
            informationRequest.setIdentityTypeFields(getIdentityTypeFields());
            informationRequest.setIdentityTypeValues(getIdentityTypeValues());
            informationRequest.setIndentityTypeId(viewableRequest.getIndentityTypeId());
            submitRequest(informationRequest);
        }
        else{
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(RequestViewActivity.this,R.style.AlertDialog);
            builder.setMessage("Identity type is not currently validated").setPositiveButton("Ok", dialogClickListener).show();
        }
    }

    private boolean hasValidId(){
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        CardType cardType = handler.getUserCardByIdentityType(userId, viewableRequest.getIndentityTypeId());
        handler.close();
        return cardType.getStatus().toUpperCase().equals(CardStatus.ACCEPTED.toString());
    }

    private String getIdentityTypeFields(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < selectedFields.size(); i++){
            builder.append(selectedFields.get(i).getType()).append(",");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    public String getIdentityTypeValues() {
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        CardType cardType = handler.getUserCardByIdentityType(userId, viewableRequest.getIndentityTypeId());
        handler.close();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < selectedFields.size(); i++){
            if(identityRequestAdapter.getBooleanList().get(i)){
                for(CardField field: cardType.getDataList()){
                    if(field.getFieldType().equals(selectedFields.get(i).getType())){
                        builder.append(field.getFieldEntry()).append(",");
                    }
                }
            }
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }


    private void networkError() {
        findViewById(R.id.request_progress).setVisibility(View.GONE);
        findViewById(R.id.request_info).setVisibility(View.VISIBLE);
        TextView info = findViewById(R.id.request_info);
        info.setText("No network connection");
    }

    private void showLoading(){
        findViewById(R.id.request_layout).setVisibility(View.GONE);
        findViewById(R.id.request_progress).setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        findViewById(R.id.request_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.request_progress).setVisibility(View.GONE);

    }

    @Override
    public void onTaskComplete(IdentityType identityType, ViewableRequest viewableRequest) {
        hideLoading();
        if(viewableRequest != null && identityType !=null){
            String status = viewableRequest.getStatus();
            if(status.equals(CardStatus.ACCEPTED.toString()) || status.equals(CardStatus.PENDING.toString()) || status.equals(CardStatus.SUBMITTED.toString())) {
                this.viewableRequest = viewableRequest;
                this.identityType = identityType;
                TextView requestTitle = findViewById(R.id.request_title);
                TextView identityTypeTitle = findViewById(R.id.identity_type_title);
                String identityTypeText = identityType.getName() + " Fields:";
                identityTypeTitle.setText(identityTypeText);
                String message = "Request ";
                if (viewableRequest.getSender().equals(profile.getServerId()))
                    message += "to " + viewableRequest.getSenderReceiverName();
                else
                    message += "from " + viewableRequest.getSenderReceiverName();
                requestTitle.setText(message);
                ListView requestedInfo = findViewById(R.id.identity_type_values);
                if(status.equals(CardStatus.PENDING.toString()) && !isRequest){
                    acceptButton.setVisibility(View.INVISIBLE);
                    rejectButton.setText("Delete");
                    String [] types = viewableRequest.getIdentityTypeFields().split(",");
                    String [] titles = getIdentityTitles(identityType,types);
                    List<CardField> cardFields = new ArrayList<>();
                    for(int i = 0; i < types.length; i++){
                        CardField cardField = new CardField("",types[i]);
                        cardField.setFieldTitle(titles[i]);
                        cardFields.add(cardField);
                    }
                    requestedInfo.setAdapter(new CardFieldListAdapter(getApplicationContext(),cardFields));
                } else if(status.equals(CardStatus.ACCEPTED.toString()) || !isRequest){
                    acceptButton.setVisibility(View.INVISIBLE);
                    rejectButton.setText("Delete");
                    String [] answers = viewableRequest.getIdentityTypeValues().split(",");
                    String [] types = viewableRequest.getIdentityTypeFields().split(",");
                    String [] titles = getIdentityTitles(identityType,types);
                    List<CardField> cardFields = new ArrayList<>();
                    for(int i = 0; i < answers.length; i++){
                        CardField cardField = new CardField(answers[i],types[i]);
                        cardField.setFieldTitle(titles[i]);
                        cardFields.add(cardField);
                    }
                    requestedInfo.setAdapter(new CardFieldListAdapter(getApplicationContext(),cardFields));
                } else{
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
            else{
                findViewById(R.id.request_layout).setVisibility(View.GONE);
                findViewById(R.id.request_progress).setVisibility(View.GONE);
                findViewById(R.id.request_info).setVisibility(View.VISIBLE);
                TextView info = findViewById(R.id.request_info);
                String message = "Request is " + status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
                info.setText(message);
            }
        }
        else{
            findViewById(R.id.request_layout).setVisibility(View.GONE);
            findViewById(R.id.request_progress).setVisibility(View.GONE);
            findViewById(R.id.request_info).setVisibility(View.VISIBLE);
            TextView info = findViewById(R.id.request_info);
            info.setText("Error retrieving data");
        }

    }

    private String [] getIdentityTitles(IdentityType identityType,String [] types){
        String [] title = new String[types.length];
        for(int i = 0; i < title.length; i++){
            title[i] = getIdentityTitle(identityType,types[i]);
        }
        return title;
    }

    private String getIdentityTitle(IdentityType identityType,String type){
        for(Field field: identityType.getFields()){
            if(field.getType().equals(type)){
                return field.getName();
            }
        }
        return "";
    }

    public void finishActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("tab",1);
        startActivity(intent);
        finish();
    }


    @Override
    public void onTaskComplete(Request request) {
        hideLoading();
        if(request != null){
            finishActivity();

        } else{
            findViewById(R.id.request_progress).setVisibility(View.GONE);
            findViewById(R.id.request_info).setVisibility(View.VISIBLE);
            TextView info = findViewById(R.id.request_info);
            info.setText("Error Submitting Request");
        }
    }
}