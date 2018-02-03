package ie.mid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ie.mid.backend.SubmissionService;
import ie.mid.handler.DatabaseHandler;
import ie.mid.model.CardType;
import ie.mid.model.Profile;
import ie.mid.model.SubmissionData;
import ie.mid.pojo.IdentityType;
import ie.mid.pojo.Submission;
import ie.mid.util.HashUtil;

public class CardSubmissionActivity extends AppCompatActivity {

    private String userId;
    private String cardId;
    private CardType cardType;
    private IdentityType identityType;
    private List<TextInputEditText> fields;
    private Profile profile;
    private static final int CAMERA_REQUEST_CODE = 1001;
    private ImageView profileImage;
    private Bitmap profilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_submission);

        profileImage = (ImageView) findViewById(R.id.profile_image);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        cardId = intent.getStringExtra("cardId");
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        cardType = handler.getUserCard(cardId);
        profile = handler.getProfile(userId);

        handler.close();


        setupView();
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
            TextInputEditText editText = new TextInputEditText(this);
            editText.setLayoutParams(editTextLayoutParams);
            editText.setHint(cardType.getDataList().get(i).getFieldTitle());
            editText.setText(cardType.getDataList().get(i).getFieldEntry());
            editText.setLines(1);
            textInputLayout.addView(editText);
            relativeLayout.addView(textInputLayout);
            fields.add(editText);
        }
        Button button = (Button) findViewById(R.id.create_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCard();
            }
        });
    }

    public void verifyCard() {
        List<String> entryList = new ArrayList<>();
        for (TextInputEditText editText : fields) {
            if (editText.getText().length() > 0) {
                entryList.add(editText.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Fields Left Empty", Toast.LENGTH_LONG).show();
                return;
            }
        }
        submitCard(entryList);
    }

    private void submitCard(List<String> entryList) {
        for(int i = 0; i < entryList.size(); i++){
            cardType.getDataList().get(i).setFieldEntry(entryList.get(i));
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        profilePhoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        String imageData = HashUtil.byteToHex(stream.toByteArray());

        Profile profile = getProfile();
        SubmissionData submissionData = new SubmissionData(imageData,cardType.getDataList());
        Submission submission = new Submission();
        submission.setData(submissionData.toString());
        submission.setUserId(profile.getServerId());
        submission.setPartyId(cardType.getPartyId());
        SubmitRunner submitRunner = new SubmitRunner(submission);
        submitRunner.run();
    }

    public Profile getProfile(){
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        Profile profile = handler.getProfile(userId);
        handler.close();
        return profile;
    }

    public void takePicture(View view){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE); }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            profilePhoto = (Bitmap) data.getExtras().get("data");
            profileImage.setImageBitmap(profilePhoto);
        }
    }

    public void finishActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("tab",1);
        startActivity(intent);
    }

    public void error(){
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
    }

    private class SubmitRunner implements Runnable{

        Submission submission;

        SubmitRunner(Submission submission){
            this.submission = submission;
        }

        @Override
        public void run() {

            SubmissionService submissionService = new SubmissionService(getApplicationContext());
            submission = submissionService.submitIdentity(submission);
            if(submission != null){
                cardType.setStatus(submission.getStatus());
                DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
                handler.open();
                handler.updateCardStatus(cardType.getId(), submission.getStatus());
                finishActivity();
            }
            else{
                error();
            }
        }
    }
}
