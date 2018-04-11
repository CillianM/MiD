package ie.mid;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ie.mid.async.SubmissionCreator;
import ie.mid.backend.SubmissionService;
import ie.mid.enums.DataType;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.SubmitTaskCompleted;
import ie.mid.model.CardType;
import ie.mid.model.Profile;
import ie.mid.model.SubmissionData;
import ie.mid.pojo.Submission;
import ie.mid.util.HashUtil;
import ie.mid.util.InternetUtil;

public class CardSubmissionActivity extends AppCompatActivity implements SubmitTaskCompleted{

    private String userId;
    private String cardId;
    private CardType cardType;
    private List<TextInputEditText> fields;
    private Profile profile;
    private static final int CAMERA_REQUEST_CODE = 1001;
    private ImageView profileImage;
    private Bitmap profilePhoto;
    Button submitButton;
    ProgressBar progressBar;
    int exp_year, exp_month, exp_day,birth_year, birth_month, birth_day;
    boolean expSet,birthSet,birthUsed,expUsed;
    DatePickerDialog.OnDateSetListener exp_dateListener,birth_dateListener;
    Button expButton,birthButton;
    int expIndex,birthIndex;
    private final int DATE_PICKER_EXP = 0;
    private final int DATE_PICKER_BIRTH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_submission);

        profileImage = (ImageView) findViewById(R.id.profile_image);
        progressBar = (ProgressBar) findViewById(R.id.submission_progress);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        cardId = intent.getStringExtra("cardId");
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        cardType = handler.getUserCard(cardId);
        profile = handler.getProfile(userId);
        birth_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if(i != 0) {
                    birthSet = true;
                    birth_year = i;
                    birth_month = i1 + 1;
                    birth_day = i2;
                    updateDisplays();
                }
            }
        };
        exp_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if(i != 0) {
                    expSet = true;
                    exp_year = i;
                    exp_month = i1 + 1;
                    exp_day = i2;
                    updateDisplays();
                }
            }
        };

        handler.close();
        setupView();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        switch(id){
            case DATE_PICKER_BIRTH:
                return new DatePickerDialog(this, birth_dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            case DATE_PICKER_EXP:
                return new DatePickerDialog(this, exp_dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    private void setupView() {
        fields = new ArrayList<>();
        float d = getResources().getDisplayMetrics().density;
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.form_layout);
        for (int i = 0; i < cardType.getDataList().size(); i++) {
            TextInputLayout textInputLayout = new TextInputLayout(this);
            textInputLayout.setId(i + 1);
            RelativeLayout.LayoutParams textInputLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            textInputLayoutParams.setMarginStart((int) (d * 15));
            textInputLayoutParams.setMarginEnd((int) (d * 15));
            if (i > 0)
                textInputLayoutParams.addRule(RelativeLayout.BELOW, i);
            textInputLayout.setLayoutParams(textInputLayoutParams);
            LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            boolean isBirthField = DataType.BIRTHDAY.toString().equals(cardType.getDataList().get(i).getFieldType());
            boolean isExpField = DataType.EXPIRY.toString().equals(cardType.getDataList().get(i).getFieldType());
            if(!isBirthField && !isExpField) {

                TextInputEditText editText = new TextInputEditText(this);
                editText.setLayoutParams(editTextLayoutParams);
                editText.setHint(cardType.getDataList().get(i).getFieldTitle());
                editText.setText(cardType.getDataList().get(i).getFieldEntry());
                editText.setSingleLine(true);
                editText.setMaxLines(1);
                editText.setLines(1);
                editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                textInputLayout.addView(editText);
                relativeLayout.addView(textInputLayout);
                fields.add(editText);
            }
            else{
                if(DataType.BIRTHDAY.toString().equals(cardType.getDataList().get(i).getFieldType())){
                    birthIndex = i;
                    birthUsed = true;
                    birthSet = true;
                    setBirthDate(cardType.getDataList().get(i).getFieldEntry());
                    birthButton = new Button(this);
                    birthButton.setBackgroundColor(getResources().getColor(R.color.grey));
                    birthButton.setTextColor(getResources().getColor(R.color.white));
                    birthButton.setText(cardType.getDataList().get(i).getFieldEntry());
                    birthButton.setLayoutParams(editTextLayoutParams);
                    birthButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialog(DATE_PICKER_BIRTH);
                        }
                    });
                    textInputLayout.addView(birthButton);
                }
                else {
                    expIndex = i;
                    expUsed = true;
                    expSet = true;
                    setExpiryDate(cardType.getDataList().get(i).getFieldEntry());
                    expButton = new Button(this);
                    expButton.setBackgroundColor(getResources().getColor(R.color.grey));
                    expButton.setTextColor(getResources().getColor(R.color.white));
                    expButton.setText(cardType.getDataList().get(i).getFieldEntry());
                    expButton.setLayoutParams(editTextLayoutParams);
                    expButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialog(DATE_PICKER_EXP);
                        }
                    });
                    textInputLayout.addView(expButton);
                }
                relativeLayout.addView(textInputLayout);
            }
        }
        submitButton = (Button) findViewById(R.id.create_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCard();
            }
        });
    }

    private void updateDisplays() {
        if(birthSet) {
            String date = birth_day + "/" + birth_month + "/" + birth_year;
            birthButton.setText(date);
        }
        if(expSet){
            String date = exp_day + "/" + exp_month + "/" + exp_year;
            expButton.setText(date);
        }
    }


    private void setBirthDate(String date){
        String[] array = date.split("/");
        birth_day = Integer.parseInt(array[0]);
        birth_month = Integer.parseInt(array[1]);
        birth_year =Integer.parseInt(array[2]);
    }

    private void setExpiryDate(String date){
        String[] array = date.split("/");
        exp_day = Integer.parseInt(array[0]);
        exp_month = Integer.parseInt(array[1]);
        exp_year =Integer.parseInt(array[2]);
    }

    public void verifyCard() {
        List<String> entryList = new ArrayList<>();
        for (int i = 0; i < fields.size() + getDateOffset(); i++) {
            if(i == birthIndex && birthSet){
                String date = birth_day + "/" + birth_month + "/" + birth_year;
                entryList.add(date);
            }
            else if(i == expIndex && expSet){
                String date = exp_day + "/" + exp_month + "/" + exp_year;
                entryList.add(date);
            }
            else{
                if (fields.get(i).getText().length() > 0 ) {
                    entryList.add(fields.get(i).getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Fields Left Empty", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        if(profilePhoto != null)
            submitCard(entryList);
        else
            Toast.makeText(getApplicationContext(), "Please submit a photo", Toast.LENGTH_LONG).show();
    }

    private void submitCard(List<String> entryList) {
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        handler.updateCard(cardType.getId(),getFieldValueString(entryList));
        handler.close();
        for(int i = 0; i < entryList.size(); i++){
            cardType.getDataList().get(i).setFieldEntry(entryList.get(i));
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        profilePhoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        String imageData = HashUtil.byteToBase64(stream.toByteArray());

        Profile profile = getProfile();
        SubmissionData submissionData = new SubmissionData(cardType.getVersionNumber(), imageData,cardType.getDataList());
        Submission submission = new Submission();
        submission.setData(submissionData.toString());
        submission.setUserId(profile.getServerId());
        submission.setPartyId(cardType.getPartyId());
        showLoading();
        if(InternetUtil.isNetworkAvailable(getApplicationContext())) {
            new SubmissionCreator(getApplicationContext(), this, new SubmissionService(getApplicationContext()),profile).execute(submission);
        }
        else{
            hideLoading();
            noInternetError();
        }
    }

    public void noInternetError(){
        Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
    }

    private String getFieldValueString(List<String> entryList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String entry : entryList) {
            String string = entry + ",";
            stringBuilder.append(string);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public Profile getProfile(){
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        Profile profile = handler.getProfile(userId);
        handler.close();
        return profile;
    }

    public void takePicture(View view){
        if (Build.VERSION.SDK_INT > 22) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permission denied to access your camera.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            profilePhoto = (Bitmap) data.getExtras().get("data");
            profileImage.setImageBitmap(profilePhoto);
            findViewById(R.id.image_prompt).setVisibility(View.GONE);
        }
    }

    public void finishActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("tab",1);
        startActivity(intent);
        finish();
    }

    public void error(){
        Toast.makeText(getApplicationContext(), "Error Processing Submission", Toast.LENGTH_LONG).show();
    }

    public int getDateOffset() {
        int offset = 0;
        if(birthSet) offset +=1;
        if(expSet) offset += 1;
        return offset;
    }

    void showLoading() {
        submitButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideLoading() {
        submitButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTaskComplete(Submission submission) {
        if(submission != null){
            cardType.setStatus(submission.getStatus());
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
            handler.open();
            handler.updateCardStatus(cardId, submission.getStatus());
            handler.updateSubmissionId(cardId, submission.getId());
            handler.createSubmission(submission.getId(),cardId);
            handler.close();
            Intent intent = new Intent(this,SubmissionViewActivity.class);
            intent.putExtra("userId",profile.getId());
            intent.putExtra("submissionId",submission.getId());
            intent.putExtra("fromCreate",true);
            startActivity(intent);
            finish();
        }
        else{
            error();
            hideLoading();
        }
    }
}
