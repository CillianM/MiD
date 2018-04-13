package ie.mid;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import ie.mid.adapter.FieldListAdapter;
import ie.mid.async.CertificateGetter;
import ie.mid.async.SubmissionGetter;
import ie.mid.enums.CardStatus;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.CertificateTaskCompleted;
import ie.mid.interfaces.SubmissionTaskCompleted;
import ie.mid.model.CreatedSubmission;
import ie.mid.model.Profile;
import ie.mid.model.SubmissionData;
import ie.mid.pojo.Certificate;
import ie.mid.pojo.Submission;
import ie.mid.util.HashUtil;
import ie.mid.util.InternetUtil;

public class SubmissionViewActivity extends AppCompatActivity implements SubmissionTaskCompleted,CertificateTaskCompleted {

    Profile profile;
    Submission submission;
    boolean fromCreate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Submission");
        setContentView(R.layout.activity_submission_view);
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        String id = getIntent().getStringExtra("userId");
        if(id != null) {
            profile = handler.getProfile(id);
        } else {
            profile = handler.getProfileByServerId(getIntent().getStringExtra("serverId"));
        }
        fromCreate = getIntent().getBooleanExtra("fromCreate", false);
        showLoading();
        if(InternetUtil.isNetworkAvailable(getApplicationContext()))
            new SubmissionGetter(getApplicationContext(),this,profile,getIntent().getStringExtra("submissionId")).execute();
        else
            networkError();
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

    private void networkError() {
        showError();
        TextView submissionInfo = findViewById(R.id.submission_info);
        submissionInfo.setText("No internet connection");
    }

    private void hideLoading(){
        findViewById(R.id.submission_view_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.submission_loading).setVisibility(View.GONE);
    }

    private void showLoading(){
        findViewById(R.id.submission_view_layout).setVisibility(View.GONE);
        findViewById(R.id.submission_loading).setVisibility(View.VISIBLE);
    }

    private void hideCertLoading(){
        findViewById(R.id.show_button).setVisibility(View.VISIBLE);
        findViewById(R.id.cert_progress).setVisibility(View.GONE);
    }

    private void showCertLoading(){
        findViewById(R.id.show_button).setVisibility(View.GONE);
        findViewById(R.id.cert_progress).setVisibility(View.VISIBLE);
    }

    private void showError(){
        findViewById(R.id.submission_info).setVisibility(View.VISIBLE);
        findViewById(R.id.submission_loading).setVisibility(View.GONE);
    }

    @Override
    public void onTaskComplete(Submission submission) {
        if(submission != null){
            if(submission.getStatus().equals(CardStatus.ACCEPTED.toString()))
                getCertificate(submission.getCertId());
            hideLoading();
            String party = "Submission to " + submission.getPartyName();
            getSupportActionBar().setTitle(party);
            TextView dateText = findViewById(R.id.date_text);
            String date = "Date submitted: " + submission.getDate();
            dateText.setText(date);
            TextView statusText = findViewById(R.id.status_text);
            String status = "Submission status: " + submission.getStatus();
            statusText.setText(status);
            ListView cardFieldsList = findViewById(R.id.card_fields);
            String submissionDataString;
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
            handler.open();
            CreatedSubmission createdSubmission = handler.getSubmission(submission.getId());
            submissionDataString = createdSubmission.getData();
            handler.close();
            SubmissionData submissionData = getSubmissionData(submissionDataString);
            if (submissionData !=null && submissionDataString != null) {
                FieldListAdapter adapter = new FieldListAdapter(getApplicationContext(),submissionData.getSubmissionFields());
                cardFieldsList.setAdapter(adapter);
                setImage(submissionData.getImageData());
            }
            else{
                showError();
                TextView submissionInfo = findViewById(R.id.submission_info);
                submissionInfo.setText("Error retrieving data from submission");
            }
        }
        else{
            showError();
            TextView submissionInfo = findViewById(R.id.submission_info);
            submissionInfo.setText("Error retrieving submission");
        }
    }

    private SubmissionData getSubmissionData(String data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(data, SubmissionData.class);
        } catch (IOException e) {
            return null;
        }
    }

    public void setImage(String data) {
        byte [] imageData = HashUtil.Base64ToByte(data);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        ImageView imageView = findViewById(R.id.submission_image);
        imageView.setImageBitmap(bitmap);
    }

    private void getCertificate(String id){
        showCertLoading();
        if (InternetUtil.isNetworkAvailable(getApplicationContext())) {
            new CertificateGetter(getApplicationContext(), this,profile, id).execute();
        } else {
            networkError();
        }
    }

    @Override
    public void onTaskComplete(final Certificate certificate) {
        hideCertLoading();
        if(certificate != null) {
            Button showButton = findViewById(R.id.show_button);
            showButton.setVisibility(View.VISIBLE);
            showButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openCertificate(certificate);
                }
            });
        } else{
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
