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

import ie.mid.adapter.RequestsListAdapter;
import ie.mid.async.RequestListGetter;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.RequestListTaskCompleted;
import ie.mid.model.Profile;
import ie.mid.model.ViewableRequest;
import ie.mid.pojo.Request;
import ie.mid.util.InternetUtil;

public class RequestListActivity extends AppCompatActivity implements RequestListTaskCompleted {
    ListView requestList;
    List<Request> requestItems;
    String userId;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        getSupportActionBar().setTitle("Current Requests");
        userId = getIntent().getStringExtra("userId");
        requestList = findViewById(R.id.requests_list);
        profile = getProfile();
        if(InternetUtil.isNetworkAvailable(getApplicationContext())) {
            new RequestListGetter(getApplicationContext(),this, profile).execute();
        }
        else{
            noInternetError();
        }
    }

    private void noInternetError(){
        TextView requestText = findViewById(R.id.requests_info);
        findViewById(R.id.requests_list_progress).setVisibility(View.GONE);
        requestText.setText("No Internet Connection");
        requestText.setVisibility(View.VISIBLE);
    }
    
    public Profile getProfile(){
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        profile = handler.getProfile(userId);
        handler.close();
        return profile;
    }

    private void getRequestsList() {
        findViewById(R.id.requests_list_progress).setVisibility(View.GONE);
        findViewById(R.id.requests_list).setVisibility(View.VISIBLE);
        RequestsListAdapter adapter = new RequestsListAdapter(getApplicationContext(),requestItems,profile.getServerId());
        requestList.setAdapter(adapter);

        // Drawer Item click listeners
        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewRequest(position);
            }
        });
    }

    private void viewRequest(int position) {
        Intent intent = new Intent(this,RequestViewActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("requestId",requestItems.get(position).getId());
        intent.putExtra("isRequest",isRequest(requestItems.get(position)));
        startActivity(intent);
    }

    public boolean isRequest(Request request) {
        if(profile.getServerId().equals(request.getRecipientId())){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void onTaskComplete(List<Request> requestList) {
        TextView requestText = findViewById(R.id.requests_info);
        findViewById(R.id.requests_list_progress).setVisibility(View.GONE);

        if(requestList != null && requestList.size() > 0) {
            requestItems = requestList;
            Collections.sort(requestItems , new Comparator<Request>() {
                @Override
                public int compare(Request t, Request t1) {
                    return t1.getCreatedAt().compareTo(t.getCreatedAt());
                }
            });
            getRequestsList();
        }
        else if(requestList != null && requestList.size() == 0) {
            requestText.setText("List Empty");
            requestText.setVisibility(View.VISIBLE);
        }
        else{

            requestText.setText("Unable To Retrieve Listing");
            requestText.setVisibility(View.VISIBLE);
        }
    }


}
