package ie.mid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ie.mid.adapter.SubmissionListAdapter;
import ie.mid.async.SubmissionListGetter;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.SubmissionListTaskCompleted;
import ie.mid.model.CardType;
import ie.mid.model.Profile;
import ie.mid.pojo.Submission;
import ie.mid.util.InternetUtil;

public class SubmissionListActivity extends AppCompatActivity implements SubmissionListTaskCompleted {
    ListView submissionList;
    List<Submission> submissionItems;
    List<CardType> cards;
    String userId;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_list);
        getSupportActionBar().setTitle("Current Submissions");
        userId = getIntent().getStringExtra("userId");
        submissionList = findViewById(R.id.submissions_list);
        profile = getProfile();
        if(InternetUtil.isNetworkAvailable(getApplicationContext())) {
            new SubmissionListGetter(getApplicationContext(),this, profile,cards).execute();
        }
        else{
            noInternetError();
        }
    }

    private void noInternetError(){
        TextView submissionText = (TextView) findViewById(R.id.submission_info);
        findViewById(R.id.requests_list_progress).setVisibility(View.GONE);
        submissionText.setText("No Internet Connection");
        submissionText.setVisibility(View.VISIBLE);
    }

    public Profile getProfile(){
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        profile = handler.getProfile(userId);
        cards = handler.getUserCards(profile.getId());
        handler.close();
        return profile;
    }

    private void getSubmissionList() {
        findViewById(R.id.submissions_list_progress).setVisibility(View.GONE);
        findViewById(R.id.submissions_list).setVisibility(View.VISIBLE);
        SubmissionListAdapter adapter = new SubmissionListAdapter(getApplicationContext(),submissionItems);
        submissionList.setAdapter(adapter);

        // Drawer Item click listeners
        submissionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewSubmission(position);
            }
        });
    }

    private void viewSubmission(int position) {
        Intent intent = new Intent(this,SubmissionViewActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("submissionId",submissionItems.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onTaskComplete(List<Submission> submissionList) {
        TextView submissionText = findViewById(R.id.submission_info);
        findViewById(R.id.submissions_list_progress).setVisibility(View.GONE);

        if(submissionList != null && submissionList.size() > 0) {
            submissionItems = submissionList;
            Collections.sort(submissionItems , new Comparator<Submission>() {
                @Override
                public int compare(Submission t, Submission t1) {
                    return t1.getDate().compareTo(t.getDate());
                }
            });
            getSubmissionList();
        }
        else if(submissionList != null && submissionList.size() == 0) {
            submissionText.setText("List Empty");
            submissionText.setVisibility(View.VISIBLE);
        }
        else{

            submissionText.setText("Unable To Retrieve Listing");
            submissionText.setVisibility(View.VISIBLE);
        }
    }
}
