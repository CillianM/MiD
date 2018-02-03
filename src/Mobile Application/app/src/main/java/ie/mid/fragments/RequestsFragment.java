package ie.mid.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ie.mid.R;
import ie.mid.adapter.RequestsListAdapter;
import ie.mid.backend.SubmissionService;
import ie.mid.handler.DatabaseHandler;
import ie.mid.model.Profile;
import ie.mid.pojo.Submission;

public class RequestsFragment extends Fragment {
    ListView submissionList;
    List<Submission> submissionItems;
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
        submissionList = view.findViewById(R.id.requests_list);
        profile = getProfile();
        SubmissionGetter submissionGetter = new SubmissionGetter();
        submissionGetter.run();
    }

    private void getSubmissionList() {
        RequestsListAdapter adapter = new RequestsListAdapter(getActivity().getApplicationContext(), submissionItems);
        submissionList.setAdapter(adapter);

        // Drawer Item click listeners
        submissionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private class SubmissionGetter implements Runnable{

        @Override
        public void run() {
            SubmissionService submissionService = new SubmissionService(getActivity().getApplicationContext());
            submissionItems = submissionService.getSubmissions(profile.getServerId());
            if(submissionItems != null)
                getSubmissionList();
        }
    }
}
