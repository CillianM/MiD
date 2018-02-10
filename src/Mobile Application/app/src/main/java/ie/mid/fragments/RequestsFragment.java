package ie.mid.fragments;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ie.mid.R;
import ie.mid.adapter.RequestsListAdapter;
import ie.mid.backend.RequestService;
import ie.mid.backend.SubmissionService;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.RequestTaskCompleted;
import ie.mid.model.Profile;
import ie.mid.pojo.Request;
import ie.mid.pojo.Submission;
import ie.mid.util.InternetUtil;

public class RequestsFragment extends Fragment implements RequestTaskCompleted {
    ListView submissionList;
    ListView requestList;
    List<Submission> submissionItems;
    List<Request> requestItems;
    String userId;
    Profile profile;

    public static RequestsFragment newInstance() {
        return new RequestsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userId = getArguments().getString("userId");
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        submissionList = view.findViewById(R.id.submissions_list);
        requestList = view.findViewById(R.id.requests_list);
        profile = getProfile();
        SubmissionService submissionService = new SubmissionService(getActivity().getApplicationContext());
        RequestService requestService = new RequestService(getActivity().getApplicationContext());
        if(InternetUtil.isNetworkAvailable(getActivity().getApplicationContext())) {
            new ListGetters(this, profile, submissionService, requestService).execute();
        }
        else{
            noInternetError();
        }
    }

    private void noInternetError(){
        TextView submissionText = (TextView) getView().findViewById(R.id.submission_info);
        TextView requestText = (TextView) getView().findViewById(R.id.requests_info);
        getView().findViewById(R.id.submissions_list_progress).setVisibility(View.GONE);
        getView().findViewById(R.id.requests_list_progress).setVisibility(View.GONE);
        submissionText.setText("No Internet Connection");
        submissionText.setVisibility(View.VISIBLE);
        requestText.setText("No Internet Connection");
        requestText.setVisibility(View.VISIBLE);
    }

    private void getSubmissionList() {
        getView().findViewById(R.id.submissions_list_progress).setVisibility(View.GONE);
        getView().findViewById(R.id.submissions_list).setVisibility(View.VISIBLE);
        RequestsListAdapter adapter = new RequestsListAdapter(getActivity().getApplicationContext());
        adapter.setSubmissions(submissionItems);
        submissionList.setAdapter(adapter);

        // Drawer Item click listeners
        submissionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void getRequestsList() {
        getView().findViewById(R.id.requests_list_progress).setVisibility(View.GONE);
        getView().findViewById(R.id.requests_list).setVisibility(View.VISIBLE);
        RequestsListAdapter adapter = new RequestsListAdapter(getActivity().getApplicationContext());
        adapter.setRequests(requestItems);
        requestList.setAdapter(adapter);

        // Drawer Item click listeners
        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public Profile getProfile(){
        DatabaseHandler handler = new DatabaseHandler(getActivity().getApplicationContext());
        handler.open();
        profile = handler.getProfile(userId);
        handler.close();
        return profile;
    }

    @Override
    public void onTaskComplete(List<Submission> submissionList, List<Request> requestList) {
        TextView submissionText = (TextView) getView().findViewById(R.id.submission_info);
        TextView requestText = (TextView) getView().findViewById(R.id.requests_info);
        getView().findViewById(R.id.submissions_list_progress).setVisibility(View.GONE);
        getView().findViewById(R.id.requests_list_progress).setVisibility(View.GONE);

        if(submissionList != null && submissionList.size() > 0) {
            submissionItems = submissionList;
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

        if(requestList != null && requestList.size() > 0) {
            requestItems = requestList;
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

    private static class ListGetters extends AsyncTask<Void, Void, String> {

        private List<Submission> submissionList;
        private List<Request> requestList;
        private Profile profile;
        private RequestTaskCompleted callBack;
        private SubmissionService submissionService;
        private RequestService requestService;

        ListGetters(RequestTaskCompleted context, Profile profile, SubmissionService submissionService, RequestService requestService){
            callBack = context;
            this.submissionService = submissionService;
            this.requestService = requestService;
            this.profile = profile;
        }

        @Override
        protected String doInBackground(Void... voids) {
            submissionList = submissionService.getSubmissions(profile.getServerId());
            requestList = requestService.getRequests(profile.getServerId());
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            callBack.onTaskComplete(submissionList,requestList);
        }

        @Override
        protected void onPreExecute() {
        }

    }
}

